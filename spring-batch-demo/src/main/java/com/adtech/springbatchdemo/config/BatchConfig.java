package com.adtech.springbatchdemo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.adtech.springbatchdemo.entity.Employee;

@Configuration
public class BatchConfig {

    // ---------------- JOB ----------------
    @Bean
    public Job employeeJob(JobRepository jobRepository, Step employeeStep) {
        return new JobBuilder("employeeJob", jobRepository)
                .start(employeeStep)
                .build();
    }

    // ---------------- STEP ----------------
    @Bean
    public Step employeeStep(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             ItemReader<Employee> reader,
                             ItemProcessor<Employee, Employee> processor,
                             ItemWriter<Employee> writer) {

        return new StepBuilder("employeeStep", jobRepository)
                .<Employee, Employee>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // ---------------- READ ----------------
    @Bean
    public FlatFileItemReader<Employee> reader() {
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input/employees.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "name", "salary");

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper =
                new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    // ---------------- PROCESS ----------------
    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return employee -> {
            employee.setSalary(employee.getSalary() * 1.1); // 10% hike
            return employee;
        };
    }

    // ---------------- WRITE ----------------
    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql(
                "INSERT INTO employee (id, name, salary) VALUES (:id, :name, :salary)"
        );
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>()
        );
        return writer;
    }
}