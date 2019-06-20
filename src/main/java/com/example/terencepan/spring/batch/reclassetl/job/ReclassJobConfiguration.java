package com.example.terencepan.spring.batch.reclassetl.job;

import com.example.terencepan.spring.batch.reclassetl.model.ReclassDto;
import com.example.terencepan.spring.batch.reclassetl.model.ReclassDtoRowMapper;
import com.example.terencepan.spring.batch.reclassetl.model.ReclassItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Configuration
public class ReclassJobConfiguration {

    private static Log logger
            = LogFactory.getLog(ReclassJobConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    DataSource datasource;

    String selectClause =
    "SELECT EVENT.EVENT_ID,\n" +
            "       EVENT.AR_ROOT_DOCUMENT,\n" +
            "       FiscalPeriod.FISCAL_YEAR_NAME                       FISCAL_YEAR,\n" +
            "       SUBEVENT_QRY.AMOUNT                                 ACCT_1200000_AMOUNT,\n" +
            "       NVL2 (ReclassExists.CURRENT_DOCUMENT, 'Y', 'N')     RECLASS_EXISTS,\n" +
            "       EventType.PROGRAM_UNIT_CODE,\n" +
            "       EVENT.EVENT_DATE,\n" +
            "       ADDRESS.FS_SEQUENCE_NUMBER,\n" +
            "       ADDRESS.STREET_ADDRESS,\n" +
            "       ADDRESS.CITY,\n" +
            "       ADDRESS.STATE_CODE,\n" +
            "       ADDRESS.ZIP_CODE,\n" +
            "       ADDRESS.COUNTRY,\n" +
            "       ADDRESS.COUNTRY_ABBREVIATION,\n" +
            "       ORGANIZATION.ORGANIZATION_NAME,\n" +
            "       ORGANIZATION.LEGAL_NAME,\n" +
            "       ORGANIZATION.DBA,\n" +
            "       ParticipantRole.CUST_ID,\n" +
            "       AccountingCode.AGENCY_SOURCE_CODE,\n" +
            "       AccountingCode.REVENUE_SOURCE_CODE,\n" +
            "       AccountingCode.INDEX_CODE,\n" +
            "       Event.DATA_SOURCE_CODE,\n" +
            "       FUND.FUND\n";

    String fromClause = "FROM EVENT\n" +
            "       INNER JOIN EVENT_TYPE EventType\n" +
            "           ON EVENT.EVENT_TYPE_ID = EventType.EVENT_TYPE_ID\n" +
            "       LEFT OUTER JOIN\n" +
            "       (SELECT DISTINCT\n" +
            "               ReclassEvent.AR_ROOT_DOCUMENT,\n" +
            "               ReclassEvent.CURRENT_DOCUMENT,\n" +
            "               REVEreclassDtoE_CURRENT_DOCUMENT\n" +
            "          FROM EVENT  ReclassEvent\n" +
            "               INNER JOIN EVENT_TYPE\n" +
            "                   ON     ReclassEvent.EVENT_TYPE_ID =\n" +
            "                          EVENT_TYPE.EVENT_TYPE_ID\n" +
            "                      AND EVENT_TYPE.EVENT_TYPE_CODE = '515'\n" +
            "               LEFT OUTER JOIN\n" +
            "               (SELECT DISTINCT\n" +
            "                       RevereclassDtoeReclassEvent.CURRENT_DOCUMENT\n" +
            "                           REVEreclassDtoE_CURRENT_DOCUMENT,\n" +
            "                       RevereclassDtoeReclassEvent.REFERENCE_DOCUMENT\n" +
            "                  FROM EVENT  RevereclassDtoeReclassEvent\n" +
            "                       INNER JOIN EVENT_TYPE\n" +
            "                           ON     RevereclassDtoeReclassEvent.EVENT_TYPE_ID =\n" +
            "                                  EVENT_TYPE.EVENT_TYPE_ID\n" +
            "                              AND EVENT_TYPE.EVENT_TYPE_CODE = '900')\n" +
            "               RevereclassDtoeReclassEvent\n" +
            "                   ON RevereclassDtoeReclassEvent.REFERENCE_DOCUMENT =\n" +
            "                      ReclassEvent.CURRENT_DOCUMENT\n" +
            "         WHERE REVEreclassDtoE_CURRENT_DOCUMENT IS NULL) ReclassExists\n" +
            "           ON ReclassExists.AR_ROOT_DOCUMENT = EVENT.AR_ROOT_DOCUMENT\n" +
            "       INNER JOIN PARTICIPANT_ROLE ParticipantRole\n" +
            "           ON EVENT.EVENT_ID = ParticipantRole.EVENT_ID\n" +
            "       INNER JOIN ADDRESS ON ADDRESS.PARTY_ID = ParticipantRole.PARTY_ID\n" +
            "       INNER JOIN ORGANIZATION\n" +
            "           ON ORGANIZATION.PARTY_ID = ParticipantRole.PARTY_ID\n" +
            "       INNER JOIN ACCTG_TRANSACT_EVENT_ASSOC\n" +
            "           ON EVENT.EVENT_ID = ACCTG_TRANSACT_EVENT_ASSOC.EVENT_ID\n" +
            "       INNER JOIN ACCOUNTING_ENTRY AccountingEntry\n" +
            "           ON AccountingEntry.ACCTG_TRANSACTION_ID =\n" +
            "              ACCTG_TRANSACT_EVENT_ASSOC.ACCTG_TRANSACTION_ID\n" +
            "       INNER JOIN ACCOUNTING_CODE AccountingCode\n" +
            "           ON AccountingEntry.ACCOUNTING_CODE_ID =\n" +
            "              AccountingCode.ACCOUNTING_CODE_ID\n" +
            "       INNER JOIN\n" +
            "       (  SELECT AR_ROOT_DOCUMENT, SUM (AMOUNT) AMOUNT\n" +
            "            FROM (SELECT EVENT.AR_ROOT_DOCUMENT,\n" +
            "                         DEBIT.AMOUNT,\n" +
            "                         EVENT.CURRENT_DOCUMENT\n" +
            "                    FROM EVENT\n" +
            "                         INNER JOIN ACCTG_TRANSACT_EVENT_ASSOC\n" +
            "                             ON EVENT.EVENT_ID =\n" +
            "                                ACCTG_TRANSACT_EVENT_ASSOC.EVENT_ID\n" +
            "                         INNER JOIN ACCOUNTING_TRANSACTION\n" +
            "                             ON ACCOUNTING_TRANSACTION.ACCTG_TRANSACTION_ID =\n" +
            "                                ACCTG_TRANSACT_EVENT_ASSOC.ACCTG_TRANSACTION_ID\n" +
            "                         INNER JOIN ACCOUNTING_ENTRY\n" +
            "                             ON ACCOUNTING_ENTRY.ACCTG_TRANSACTION_ID =\n" +
            "                                ACCOUNTING_TRANSACTION.ACCTG_TRANSACTION_ID\n" +
            "                         INNER JOIN DEBIT\n" +
            "                             ON     DEBIT.ACCTG_ENTRY_ID =\n" +
            "                                    ACCOUNTING_ENTRY.ACCTG_ENTRY_ID\n" +
            "                                AND DEBIT.ACCOUNT_NUMBER = '1200000'\n" +
            "                  UNION ALL\n" +
            "                  SELECT EVENT.AR_ROOT_DOCUMENT,\n" +
            "                         CREDIT.AMOUNT,\n" +
            "                         EVENT.CURRENT_DOCUMENT\n" +
            "                    FROM EVENT\n" +
            "                         INNER JOIN ACCTG_TRANSACT_EVENT_ASSOC\n" +
            "                             ON EVENT.EVENT_ID =\n" +
            "                                ACCTG_TRANSACT_EVENT_ASSOC.EVENT_ID\n" +
            "                         INNER JOIN ACCOUNTING_TRANSACTION\n" +
            "                             ON ACCOUNTING_TRANSACTION.ACCTG_TRANSACTION_ID =\n" +
            "                                ACCTG_TRANSACT_EVENT_ASSOC.ACCTG_TRANSACTION_ID\n" +
            "                         INNER JOIN ACCOUNTING_ENTRY\n" +
            "                             ON ACCOUNTING_ENTRY.ACCTG_TRANSACTION_ID =\n" +
            "                                ACCOUNTING_TRANSACTION.ACCTG_TRANSACTION_ID\n" +
            "                         INNER JOIN CREDIT\n" +
            "                             ON     CREDIT.ACCTG_ENTRY_ID =\n" +
            "                                    ACCOUNTING_ENTRY.ACCTG_ENTRY_ID\n" +
            "                                AND CREDIT.ACCOUNT_NUMBER = '1200000')\n" +
            "        GROUP BY AR_ROOT_DOCUMENT) SUBEVENT_QRY\n" +
            "           ON EVENT.AR_ROOT_DOCUMENT = SUBEVENT_QRY.AR_ROOT_DOCUMENT\n" +
            "       INNER JOIN FUND_ALLOCATION FundAllocation\n" +
            "           ON FundAllocation.FUND_ALLOCATION_ID =\n" +
            "              AccountingCode.FUND_ALLOCATION_ID\n" +
            "       INNER JOIN FUND ON FUND.FUND_ID = FundAllocation.FUND_TO_ID\n" +
            "       INNER JOIN FISCAL_PERIOD FiscalPeriod\n" +
            "           ON FiscalPeriod.FISCAL_PERIOD_ID = FUND.FISCAL_PERIOD_ID\n";

    String whereClause = " WHERE     EventType.EVENT_TYPE_CODE = '010'\n" +
            "       AND ReclassExists.CURRENT_DOCUMENT IS NULL\n" +
            "       --AND PROGRAM_UNIT_CODE = 'PV'\n" +
            "       AND SUBEVENT_QRY.AMOUNT >= 0\n" +
            "       AND FISCAL_YEAR_NAME IN ('2016', '2017')\n" +
            "       AND rownum < 101";

    //Kinda like the extract
    @Bean
    public ItemReader<ReclassDto> reclassDtoItemReader() throws Exception{
        JdbcPagingItemReader<ReclassDto> jdbcPagingItemReader =
                new JdbcPagingItemReader<>();

        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean =
                new SqlPagingQueryProviderFactoryBean();

        queryProviderFactoryBean.setDataSource(datasource);
        queryProviderFactoryBean.setDatabaseType("ORACLE");
        queryProviderFactoryBean.setSelectClause(selectClause);
        queryProviderFactoryBean.setFromClause(fromClause);
        queryProviderFactoryBean.setWhereClause(whereClause);
        //queryProviderFactoryBean.setSortKey("");

        jdbcPagingItemReader.setDataSource(datasource);
        jdbcPagingItemReader.setFetchSize(20);
        jdbcPagingItemReader.setRowMapper(new ReclassDtoRowMapper());


        jdbcPagingItemReader.setQueryProvider(queryProviderFactoryBean.getObject());

        return jdbcPagingItemReader;
    }

    //Kinda like the transform
    @Bean
    public ItemProcessor<ReclassDto, ReclassItem> itemProcessor() {
        return new CustomItemProcessor();
    }

    //Kinda like the load
    @Bean
    public ItemWriter<ReclassDto> itemWriter(){

    }

    @Bean
    public Job reclassJob() {
        return jobBuilderFactory.get("reclassJob")
                .start(stepBuilderFactory.get("reclassJobGetDataStep")
                        .tasklet(new Tasklet() {
                            @Override
                            public RepeatStatus execute(StepContribution contribution,
                                                        ChunkContext chunkContext) throws Exception {

                                logger.info("Reclass Job ran");
                                return RepeatStatus.FINISHED;
                            }
                        }).build()).build();
    }

    private class CustomItemProcessor implements ItemProcessor<ReclassDto, ReclassItem> {

        public ReclassItem process(ReclassDto reclassDto) {
            ReclassItem reclassItem = new ReclassItem();

            reclassItem.setEventId(reclassDto.getEventId());
            reclassItem.setArRootDocument(reclassDto.getArRootDocument());
            reclassItem.setFiscalYear(reclassDto.getFiscalYear());
            reclassItem.setAcct1200000Amount(reclassDto.getAcct1200000Amount());
            reclassItem.setReclassExists(reclassDto.getReclassExists().equals("Y") ? true : false);
            reclassItem.setProgramUnitCode(reclassDto.getProgramUnitCode());
            reclassItem.setEventDate(reclassDto.getEventDate());
            reclassItem.setFsSequenceNumber(reclassDto.getFsSequenceNumber());
            reclassItem.setStreetAddress(reclassDto.getStreetAddress());
            reclassItem.setCity(reclassDto.getCity());
            reclassItem.setStateCode(reclassDto.getStateCode());
            reclassItem.setZipCode(reclassDto.getZipCode());
            reclassItem.setCountry(reclassDto.getCountry());
            reclassItem.setCountryCode(reclassDto.getCountryCode());
            reclassItem.setOrganizationName(reclassDto.getOrganizationName());
            reclassItem.setLegalName(reclassDto.getLegalName());
            reclassItem.setDba(reclassDto.getDba());
            reclassItem.setCustId(reclassDto.getCustId());
            reclassItem.setAgencySourceCode(reclassDto.getAgencySourceCode());
            reclassItem.setIndexCode(reclassDto.getIndexCode());
            reclassItem.setDataSourceCode(reclassDto.getDataSourceCode());
            reclassItem.setFund(reclassDto.getFund());
            reclassItem.setCreatedBy("CARS");
            reclassItem.setCreatedDate(LocalDateTime.now());

            return reclassItem;
        }
    }
}
