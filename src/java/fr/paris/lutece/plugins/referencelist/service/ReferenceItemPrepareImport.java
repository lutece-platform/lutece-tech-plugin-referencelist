/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
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
import fr.paris.lutece.portal.service.util.AppLogService;

public class ReferenceItemPrepareImport
{

    private static final String CONSTANT_POINT = ".";
    private static final String CONSTANT_SEPARATOR = ";";
    private static final String CONSTANT_FILE_EXTENTION = "csv";
    private static final int CONSTANT_FILE_NUMOFCOLS = 2;

    private static final String CONSTANT_ERROR_INVALID_RECORD = "Invalid record on line ";
    private static final String CONSTANT_ERROR_INVALID_DUPLICATE = "Duplicate name on line ";
    private static final String CONSTANT_ERROR_INVALID_NUMOFCOLS = "Num of Col is not equal of 2";

    public ReferenceItemPrepareImport( )
    {
    }

    /**
     * Check CSV File.
     * 
     * @param strFileName
     *            The filename
     * @param FileSize
     *            The size of file
     * @return false if FileName || FileExtention || FileSize doesnt match to constraints.
     */
    public static boolean isImportableCSVFile( String strFileName, long FileSize )
    {

        String strFileExtention;

        // Check File Name
        if ( StringUtils.isNotEmpty( strFileName ) && strFileName.contains( CONSTANT_POINT ) )
        {
            strFileExtention = strFileName.substring( strFileName.lastIndexOf( CONSTANT_POINT ) + 1 );
        }
        else
        {
            return false;
        }
        // Check File Extention
        if ( !strFileExtention.toLowerCase( ).equals( CONSTANT_FILE_EXTENTION ) )
        {
            return false;
        }

        // Check Empty File
        if ( FileSize < 6 )
        {
            return false;
        }

        // Ready for Import;
        return true;
    };

    /**
     * Check if CSV file contains errors
     * 
     * @param fileInputStream
     *            The fileInputStream to check
     * @return a String with the import source errors
     */
    public static String isErrorInCSVFile( InputStream fileInputStream )
    {
        String errorsMessages = "";

        List<ReferenceItem> list = new ArrayList<>( );
        Reader _reader;
        _reader = new InputStreamReader( fileInputStream );

        if ( _reader != null )
        {
            int i = 0;
            Scanner scanner = new Scanner( _reader );

            while ( scanner.hasNextLine( ) )
            {
                i++;
                String strLine = scanner.nextLine( );
                String [ ] strFields = strLine.split( CONSTANT_SEPARATOR );

                if ( strFields.length == CONSTANT_FILE_NUMOFCOLS )
                {
                    if ( isDuplicateName( list, strFields [1] ) )
                    {
                        errorsMessages += CONSTANT_ERROR_INVALID_DUPLICATE + i + "\r\n";
                    }
                    else
                    {
                        ReferenceItem referenceItem = new ReferenceItem( );

                        referenceItem.setCode( strFields [0] );
                        referenceItem.setName( strFields [1] );

                        list.add( referenceItem );
                    }

                }
                else
                {
                    errorsMessages += CONSTANT_ERROR_INVALID_RECORD + i + " : " + CONSTANT_ERROR_INVALID_NUMOFCOLS + " (=" + strFields.length + ")  \r\n";
                }

            }
            scanner.close( );
        }

        if ( errorsMessages.length( ) > 0 )
        {
            return getHtmlLinkBase64Src( errorsMessages );
        }
        else
        {
            return null;
        }

    }

    /**
     * CSV Import for a specific Referential.
     * 
     * @param fileInputStream
     *            The fileInputStream to read data from
     * @param refId
     *            ID of Reference
     * @return a String with the import source result or null if an error occurs during the instantiation of the import source.
     */
    public static List<ReferenceItem> findCandidateItems( InputStream fileInputStream, int refId )
    {
        List<ReferenceItem> list = new ArrayList<>( );

        Reader _reader;
        _reader = new InputStreamReader( fileInputStream );

        if ( _reader != null )
        {
            Scanner scanner = new Scanner( _reader );

            while ( scanner.hasNextLine( ) )
            {
                String strLine = scanner.nextLine( );
                String [ ] strFields = strLine.split( CONSTANT_SEPARATOR );

                if ( strFields.length == CONSTANT_FILE_NUMOFCOLS )
                {
                    if ( !isDuplicateName( list, strFields [1] ) )
                    {
                        ReferenceItem referenceItem = new ReferenceItem( );

                        referenceItem.setCode( strFields [0] );
                        referenceItem.setName( strFields [1] );

                        referenceItem.setIdreference( refId );
                        list.add( referenceItem );
                    }
                }
            }
            scanner.close( );
        }
        return list;
    }

    public static boolean isDuplicateName( List<ReferenceItem> list, String candidateItemName )
    {

        boolean checker = false;

        for ( ReferenceItem referenceItem : list )
        {
            // compare names;
            if ( candidateItemName.equals( referenceItem.getName( ) ) )
            {
                checker = true;
            }
        }

        return checker;

    }

    /**
     * Generate HTML aLink based on a Base64 text file.
     * 
     * @param strFileName
     *            Name of import file
     * @param strFileMessage
     *            Message to include in text file
     * @param errorsCount
     *            Number of errors
     * @return Return null if UnsupportedEncodingException and return a base64 text file src
     * 
     */
    private static String getHtmlLinkBase64Src( String strFileMessage )
    {
        byte [ ] encodedBytes = Base64.encodeBase64( strFileMessage.getBytes( ) );
        try
        {
            return new String( encodedBytes, "UTF-8" );
        }
        catch( UnsupportedEncodingException e )
        {
            AppLogService.error( "Unable to convert byte to string for logfile errors" );
            return null;
        }
    }

}
