package io.javabrains.ipldashboard.data;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.javabrains.ipldashboard.JobCompletionNotificationListener;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
  String[] fieldNames=new String[] { "match_id", "season", "date", "city", "venue", "team1", "team2", "toss_winner", "toss_decision", "player_of_match", "winner", "winner_wickets", "winner_runs", "outcome", "result_type", "results", "gender", "event", "match_number", "umpire1", "umpire2", "reserve_umpire", "tv_umpire", "match_referee", "eliminator", "method", "date_1"};
	
 

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public FlatFileItemReader<MatchInput> reader() {
    return new FlatFileItemReaderBuilder<MatchInput>()
      .name("matchItemReader")
      .resource(new ClassPathResource("ipl_match_info_data.csv"))
      .delimited()
      .names(fieldNames)
      .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {{
        setTargetType(MatchInput.class);
      }})
      .build();
  }

  @Bean
  public IPLDataProcessor processor() {
    return new IPLDataProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Match>()
      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
      .sql("INSERT INTO match  (id,date,city,venue,team1,team2,toss_winner,toss_decision,player_of_match,match_winner,results,umpire1,umpire2) VALUES (:id, :date, :city, :venue, :team1, :team2, :tossWinner, :tossDecision, :playerOfMatch, :matchWinner, :results, :umpire1, :umpire2)")
      .dataSource(dataSource)
      .build();
  }

  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("importUserJob")
      .incrementer(new RunIdIncrementer())
      .listener(listener)
      .flow(step1)
      .end()
      .build();
  }

  @Bean
  public Step step1(JdbcBatchItemWriter<Match> writer) {
    return stepBuilderFactory.get("step1")
      .<MatchInput, Match> chunk(10)
      .reader(reader())
      .processor(processor())
      .writer(writer)
      .build();
  }
}
