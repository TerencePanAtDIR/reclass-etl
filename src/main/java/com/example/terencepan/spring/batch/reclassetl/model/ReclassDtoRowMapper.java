package com.example.terencepan.spring.batch.reclassetl.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReclassDtoRowMapper implements RowMapper<ReclassDto> {

    @Override
    public ReclassDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReclassDto reclassDto = new ReclassDto();

        reclassDto.setEventId(rs.getLong("event_id"));
        reclassDto.setArRootDocument(rs.getString("ar_root_document"));
        reclassDto.setFiscalYear(rs.getString("fiscal_year"));
        reclassDto.setAcct1200000Amount(rs.getBigDecimal("acct_1200000_amount"));
        reclassDto.setReclassExists(rs.getString("reclass_exists"));
        reclassDto.setProgramUnitCode(rs.getString("program_unit_code"));
        reclassDto.setEventDate(rs.getDate("event_date").toLocalDate());
        reclassDto.setFsSequenceNumber(rs.getString("fs_sequence_number"));
        reclassDto.setStreetAddress(rs.getString("street_address"));
        reclassDto.setCity(rs.getString("city"));
        reclassDto.setStateCode(rs.getString("state_code"));
        reclassDto.setZipCode(rs.getString("zip_code"));
        reclassDto.setCountry(rs.getString("country"));
        reclassDto.setCountryCode(rs.getString("country_abbreviation"));
        reclassDto.setOrganizationName(rs.getString("organization_name"));
        reclassDto.setLegalName(rs.getString("legal_name"));
        reclassDto.setDba(rs.getString("dba"));
        reclassDto.setCustId(rs.getString("cust_id"));
        reclassDto.setAgencySourceCode(rs.getString("agency_source_code"));
        reclassDto.setIndexCode(rs.getString("index_code"));
        reclassDto.setDataSourceCode(rs.getString("data_source_code"));
        reclassDto.setFund(rs.getString("fund"));

        return reclassDto;
    }


}
