package fr.paris.lutece.plugins.referencelist.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;

public class ReferenceItemPrepareImport {

    private static final String CONSTANT_POINT = ".";
    private static final String CONSTANT_SEPARATOR = ";";
    private static final String CONSTANT_FILE_EXTENTION = "csv";
    private static final int CONSTANT_FILE_NUMOFCOLS = 2;

    private static final String CONSTANT_ERROR_INVALID_RECORD = "Invalid record on line ";
    private static final String CONSTANT_ERROR_INVALID_DUPLICATE = "Duplicate name on line ";
    private static final String CONSTANT_ERROR_INVALID_NUMOFCOLS = "Num of Col is not equal of 2";

    public ReferenceItemPrepareImport() {
    }

    /**
     * Check CSV File.
     * 
     * @param fileItem The file item to import
     * @return the fileItem or null if FileName || FileExtention || FileSize doesnt
     *         match to constraints.
     */
    public static boolean isImportableCSVFile(String strFileName, long FileSize) {

        String strFileExtention;

        // Check File Name
        if (StringUtils.isNotEmpty(strFileName) && strFileName.contains(CONSTANT_POINT)) {
            strFileExtention = strFileName.substring(strFileName.lastIndexOf(CONSTANT_POINT) + 1);
        } else {
            return false;
        }
        // Check File Extention
        if (!strFileExtention.toLowerCase().equals(CONSTANT_FILE_EXTENTION)) {
            return false;
        }

        // Check Empty File
        if (FileSize < 6) {
            return false;
        }

        // Ready for Import;
        return true;
    };

    /**
     * Check if CSV file contains errors
     * 
     * @param fileInputStream The fileInputStream to check
     * @return a String with the import source errors
     */
    public static String isErrorInCSVFile(InputStream fileInputStream) {
        String errorsMessages = "";
        int errorsCount = 0;
        List<ReferenceItem> list = new ArrayList<>();
        Reader _reader;
        _reader = new InputStreamReader(fileInputStream);
        if (_reader != null) {
            int i = 0;
            Scanner scanner = new Scanner(_reader);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                i++;
                String strLine = scanner.nextLine();
                String[] strFields = strLine.split(CONSTANT_SEPARATOR);
                if (strFields.length == CONSTANT_FILE_NUMOFCOLS) {
                    if (isDuplicateName(list, strFields[0])) {
                        errorsMessages += CONSTANT_ERROR_INVALID_DUPLICATE + i + "\r\n";
                        errorsCount++;
                    } else {
                        ReferenceItem referenceItem = new ReferenceItem();
                        referenceItem.setItemName(strFields[0]);
                        referenceItem.setItemValue(strFields[1]);
                        referenceItem.setIdreference(1);
                        list.add(referenceItem);
                    }

                } else {
                    errorsMessages += CONSTANT_ERROR_INVALID_RECORD + i + " : " + CONSTANT_ERROR_INVALID_NUMOFCOLS
                            + " (=" + strFields.length + ")  \r\n";
                    errorsCount++;
                }

            }
            scanner.close();
        }

        if (errorsMessages.length() > 0) {
            return getHtmlLinkBase64Src("log_file", errorsMessages, errorsCount);
        } else {
            return null;
        }

    }

    /**
     * CSV Import for a specific Referential.
     * 
     * @param fileItem The file item to read data from
     * @param refId    ID of Reference
     * @return a String with the import source result or null if an error occurs
     *         during the instantiation of the import source.
     */
    public static List<ReferenceItem> findCandidateItems(InputStream fileInputStream, int refId) {
        List<ReferenceItem> list = new ArrayList<>();

        List<Object> result = new ArrayList<>();
        Reader _reader;
        _reader = new InputStreamReader(fileInputStream);
        if (_reader != null) {
            int i = 0;
            Scanner scanner = new Scanner(_reader);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                i++;
                String strLine = scanner.nextLine();
                String[] strFields = strLine.split(CONSTANT_SEPARATOR);
                if (strFields.length == CONSTANT_FILE_NUMOFCOLS) {
                    if (!isDuplicateName(list, strFields[0])) {
                        ReferenceItem referenceItem = new ReferenceItem();
                        referenceItem.setItemName(strFields[0]);
                        referenceItem.setItemValue(strFields[1]);
                        referenceItem.setIdreference(refId);
                        list.add(referenceItem);
                    }
                }
            }
            scanner.close();
        }
        return list;
    }

    public static boolean isDuplicateName(List<ReferenceItem> list, String candidateItemName) {

        boolean checker = false;

        for (ReferenceItem referenceItem : list) {
            // compare names;
            if (candidateItemName.equals(referenceItem.getItemName())) {
                checker = true;
            }
        }

        return checker;

    }

    /**
     * Generate HTML aLink based on a Base64 text file.
     * 
     * @param strFileName    Name of import file
     * @param strFileMessage Message to include in text file
     * @param errorsCount    Number of errors
     * @return Return null if one of parameters is null or return a Html link with
     *         base64 text file src
     * @throws UnsupportedEncodingException
     * 
     */
    private static String getHtmlLinkBase64Src(String strFileName, String strFileMessage, int errorsCount) {
        byte[] encodedBytes = Base64.encodeBase64(strFileMessage.getBytes());
        String strencodedBytes;
        try {
            strencodedBytes = new String(encodedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        String strLink = " - <a style='' download='" + strFileName + ".txt' href='data:text/plain;base64,"
                + strencodedBytes + "'><strong>" + errorsCount + " error(s)</strong></a> ";
        return strLink;
    }

}