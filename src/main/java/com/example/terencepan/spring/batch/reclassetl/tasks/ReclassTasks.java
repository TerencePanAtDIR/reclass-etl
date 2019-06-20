package com.example.terencepan.spring.batch.reclassetl.tasks;

import com.example.terencepan.spring.batch.reclassetl.model.ReclassItem;
import com.example.terencepan.spring.batch.reclassetl.utils.GeneralUtils;
import gov.ca.dir.acct.cars.webservices.internal.data.CARS515EventDetails;
import gov.ca.dir.acct.cars.webservices.internal.data.CARSPacketDetails;
import gov.ca.dir.acct.cars.webservices.internal.proxy.CARSIntakeWebService;
import gov.ca.dir.acct.cars.webservices.internal.proxy.Execute_ptt;
import gov.ca.dir.acct.cars.webservices.internal.types.CARS515Event;
import gov.ca.dir.acct.cars.webservices.internal.types.CARSPacket;
import gov.ca.dir.acct.cars.webservices.internal.types.common.TEventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.ws.BindingProvider;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ReclassTasks {

    private static Log logger
            = LogFactory.getLog(ReclassTasks.class);

    public static CARS515Event prepareCarsReclassEvent(ReclassItem reclassItem){

        //Simple Date Format
        SimpleDateFormat sd3 = new SimpleDateFormat("MM/dd/yyyy");

        //CARS515EventBuilder
        CARS515EventDetails.CARS515EventBuilder cars515EventBuilder =
                new CARS515EventDetails.CARS515EventBuilder();

        //Get reclass note date text
        String reclassNoteDateText = "Reclassified AR on " +
                sd3.format(LocalDate.now()) +
                " to a deferred account per SAM Section 8776 and 10610.";

        //Simple Date Format
        SimpleDateFormat sd2 = new SimpleDateFormat("MMddyyyy");

        CARS515Event cars515event =
                cars515EventBuilder
                        .setCurrentDocument(reclassItem.getArRootDocument() + "-515-" + sd2.format(sd2.format(LocalDate.now())))
                        .setArRootDocument(reclassItem.getArRootDocument())
                        .setReferenceDocument(reclassItem.getArRootDocument())
                        .setEventDate(GeneralUtils.getXMLGregorianCalendarNow())
                        .setDataSourceCode(reclassItem.getDataSourceCode())
                        .setProgramUnitCode(reclassItem.getProgramUnitCode())
                        .setSubmitBy("CARS")
                        .setSubmitDate(GeneralUtils.getXMLGregorianCalendarNow())
                        .setNote(reclassNoteDateText)
                        .setAmount(reclassItem.getAcct1200000Amount())
                        .setAccountingCodeAgencySourceCode(reclassItem.getAgencySourceCode())
                        .setAccountingCodeRevenueCode(reclassItem.getRevenueSourceCode())
                        .setAccountingCodeIndexCode(reclassItem.getIndexCode())
                        .setParticipantParticipantRole("RES_PARTY")
                        .setParticipantPartyNumber(BigInteger.valueOf(1))
                        .setOrgOrganizationName(reclassItem.getOrganizationName())
                        .setOrgDbaName(reclassItem.getDba())
                        .setOrgLegalName(reclassItem.getLegalName())
                        .setAddressStreetAddress(reclassItem.getStreetAddress())
                        .setAddressCity(reclassItem.getCity())
                        .setAddressState(reclassItem.getStateCode())
                        .setAddressZipCode(reclassItem.getZipCode())
                        .createCARS515Event();

        return cars515event;
    }

    public static CARSPacket prepareReclassEventCarsPacket(CARS515Event cars515Event){

        //Simple Date Format
        SimpleDateFormat sd2 = new SimpleDateFormat("MMddyyyy");

        //CARSPacketBuilder
        CARSPacketDetails.CARSPacketBuilder carsPacketBuilder =
                new CARSPacketDetails.CARSPacketBuilder();

        CARSPacket carsPacket =
                carsPacketBuilder.setDataSourceCode(cars515Event.getCARSEventHeader().getDataSourceCode().toString())
                        .setEnvironment("DEV")
                        .setPacketID(cars515Event.getCARSEventDetail().getRootDocument() + "-515-" + sd2.format(LocalDate.now()))
                        .setPacketPayload(cars515Event)
                        .setPacketPayloadInformation(TEventType.CARS_515_EVENT)
                        .setPacketTarget("CARS")
                        .setPacketTransport("WS")
                        .setProgramUnitCode(cars515Event.getCARSEventHeader().getProgramUnitCode().toString())
                        .setSubmitBy("CARS")
                        .setSubmitDate(GeneralUtils.getXMLGregorianCalendarNow())
                        .createCARSPacketDetails();

        return carsPacket;
    }

    public static void sendReclassCarsPacket(CARSPacket carsPacket){
        try {
            logger.info("Sending 515 Event to CARS");

            CARSIntakeWebService carsIntakeWebService = new CARSIntakeWebService();
            Execute_ptt execute_ptt = carsIntakeWebService.getExecute_pt();
            ((BindingProvider) execute_ptt).getRequestContext();
            CARSPacket pkt = carsPacket;

            execute_ptt.execute(pkt);
            //debug code for xml output
            //testXmlPayload(pkt);

        } catch (Exception e) {
            logger.error(e);
        }

    }

}
