package com.example.terencepan.spring.batch.reclassetl.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ReclassDto {
    private long eventId;
    private String arRootDocument;
    private String fiscalYear;
    private BigDecimal acct1200000Amount;
    private String reclassExists;
    private String programUnitCode;
    private LocalDate eventDate;
    private String fsSequenceNumber;
    private String streetAddress;
    private String city;
    private String stateCode;
    private String zipCode;
    private String countryCode;
    private String country;
    private String organizationName;
    private String legalName;
    private String dba;
    private String custId;
    private String agencySourceCode;
    private String revenueSourceCode;
    private String indexCode;
    private String dataSourceCode;
    private String fund;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getArRootDocument() {
        return arRootDocument;
    }

    public void setArRootDocument(String arRootDocument) {
        this.arRootDocument = arRootDocument;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public BigDecimal getAcct1200000Amount() {
        return acct1200000Amount;
    }

    public void setAcct1200000Amount(BigDecimal acct1200000Amount) {
        this.acct1200000Amount = acct1200000Amount;
    }

    public String getReclassExists() {
        return reclassExists;
    }

    public void setReclassExists(String reclassExists) {
        this.reclassExists = reclassExists;
    }

    public String getProgramUnitCode() {
        return programUnitCode;
    }

    public void setProgramUnitCode(String programUnitCode) {
        this.programUnitCode = programUnitCode;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getFsSequenceNumber() {
        return fsSequenceNumber;
    }

    public void setFsSequenceNumber(String fsSequenceNumber) {
        this.fsSequenceNumber = fsSequenceNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getDba() {
        return dba;
    }

    public void setDba(String dba) {
        this.dba = dba;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAgencySourceCode() {
        return agencySourceCode;
    }

    public void setAgencySourceCode(String agencySourceCode) {
        this.agencySourceCode = agencySourceCode;
    }

    public String getRevenueSourceCode() {
        return revenueSourceCode;
    }

    public void setRevenueSourceCode(String revenueSourceCode) {
        this.revenueSourceCode = revenueSourceCode;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReclassDto that = (ReclassDto) o;
        return eventId == that.eventId &&
                Objects.equals(arRootDocument, that.arRootDocument) &&
                Objects.equals(fiscalYear, that.fiscalYear) &&
                Objects.equals(acct1200000Amount, that.acct1200000Amount) &&
                Objects.equals(reclassExists, that.reclassExists) &&
                Objects.equals(programUnitCode, that.programUnitCode) &&
                Objects.equals(eventDate, that.eventDate) &&
                Objects.equals(fsSequenceNumber, that.fsSequenceNumber) &&
                Objects.equals(streetAddress, that.streetAddress) &&
                Objects.equals(city, that.city) &&
                Objects.equals(stateCode, that.stateCode) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(country, that.country) &&
                Objects.equals(organizationName, that.organizationName) &&
                Objects.equals(legalName, that.legalName) &&
                Objects.equals(dba, that.dba) &&
                Objects.equals(custId, that.custId) &&
                Objects.equals(agencySourceCode, that.agencySourceCode) &&
                Objects.equals(revenueSourceCode, that.revenueSourceCode) &&
                Objects.equals(indexCode, that.indexCode) &&
                Objects.equals(dataSourceCode, that.dataSourceCode) &&
                Objects.equals(fund, that.fund);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, arRootDocument, fiscalYear, acct1200000Amount, reclassExists, programUnitCode, eventDate, fsSequenceNumber, streetAddress, city, stateCode, zipCode, countryCode, country, organizationName, legalName, dba, custId, agencySourceCode, revenueSourceCode, indexCode, dataSourceCode, fund);
    }

    @Override
    public String toString() {
        return "ReclassDto{" +
                "eventId=" + eventId +
                ", arRootDocument=" + arRootDocument +
                ", fiscalYear='" + fiscalYear + '\'' +
                ", acct1200000Amount=" + acct1200000Amount +
                ", reclassExists='" + reclassExists + '\'' +
                ", programUnitCode='" + programUnitCode + '\'' +
                ", eventDate=" + eventDate +
                ", fsSequenceNumber='" + fsSequenceNumber + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", country='" + country + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", dba='" + dba + '\'' +
                ", custId='" + custId + '\'' +
                ", agencySourceCode='" + agencySourceCode + '\'' +
                ", revenueSourceCode='" + revenueSourceCode + '\'' +
                ", indexCode='" + indexCode + '\'' +
                ", dataSourceCode='" + dataSourceCode + '\'' +
                ", fund='" + fund + '\'' +
                '}';
    }
}
