package com.example.terencepan.spring.batch.reclassetl.utils;

import gov.ca.dir.acct.cars.webservices.internal.types.CARSPacket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.GregorianCalendar;

public class GeneralUtils {

    private static Log logger
            = LogFactory.getLog(GeneralUtils.class);

    public static XMLGregorianCalendar getXMLGregorianCalendarNow() {
        try {
            XMLGregorianCalendar now;
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            now =
                    datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
            return now;
        } catch (DatatypeConfigurationException e) {
            logger.info(e);
        }
        return null;
    }

    //Code used for printing out xml file
    public static void testXmlPayload(CARSPacket pkt){
        try{
            JAXBContext context =
                    JAXBContext.newInstance(pkt.getClass(),pkt.getClass());

            Unmarshaller u = context.createUnmarshaller();
            Marshaller marshaller = context.createMarshaller();
            Source source = new JAXBSource(marshaller, pkt);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            OutputStream os = new FileOutputStream("c:\\tempstorage\\marshalled.xml");
            marshaller.marshal(pkt, os);
            marshaller.marshal(pkt, System.out);
        } catch (Exception e){
            logger.info(e);
        }
    }
}
