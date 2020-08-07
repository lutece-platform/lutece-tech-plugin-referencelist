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
package fr.paris.lutece.plugins.referencelist.business;

import java.util.List;
import java.util.Locale;

import fr.paris.lutece.portal.service.i18n.I18nService;

/**
 * This class provides instances management methods (create, find, ...) for Result of ReferenceItemPrepareImport
 */
public class CompareResult
{
    List<ReferenceItem> _updateListCandidateReferenceItems;
    List<ReferenceItem> _duplicateListCandidateReferenceItems;
    List<ReferenceItem> _insertListCandidateReferenceItems;
    String messageResult = "";

    private static final String INFO_REFERENCEITEM_DUPLICATE_IN_TABLE = "referencelist.info.referenceitem.import.duplicateintable";
    private static final String INFO_REFERENCEITEM_TO_UPDATE = "referencelist.info.referenceitem.import.updated";
    private static final String INFO_REFERENCEITEM_TO_INSERT = "referencelist.info.referenceitem.import.toinsert";

    /**
     * 
     * @param insertListCandidateReferenceItems
     * @param updateListCandidateReferenceItems
     * @param duplicateListCandidateReferenceItems
     */
    public CompareResult( List<ReferenceItem> insertListCandidateReferenceItems, List<ReferenceItem> updateListCandidateReferenceItems,
            List<ReferenceItem> duplicateListCandidateReferenceItems )
    {
        set_updateListCandidateReferenceItems( updateListCandidateReferenceItems );
        set_duplicateListCandidateReferenceItems( duplicateListCandidateReferenceItems );
        set_insertListCandidateReferenceItems( insertListCandidateReferenceItems );
    }

    public String getMessageResult( )
    {
        return messageResult;
    }

    public void setMessageResult( String messageResult )
    {
        this.messageResult = messageResult;
    }

    public List<ReferenceItem> get_updateListCandidateReferenceItems( )
    {
        return _updateListCandidateReferenceItems;
    }

    public void set_updateListCandidateReferenceItems( List<ReferenceItem> _updateListCandidateReferenceItems )
    {
        this._updateListCandidateReferenceItems = _updateListCandidateReferenceItems;
    }

    public List<ReferenceItem> get_duplicateListCandidateReferenceItems( )
    {
        return _duplicateListCandidateReferenceItems;
    }

    public void set_duplicateListCandidateReferenceItems( List<ReferenceItem> _duplicateListCandidateReferenceItems )
    {
        this._duplicateListCandidateReferenceItems = _duplicateListCandidateReferenceItems;
    }

    public List<ReferenceItem> get_insertListCandidateReferenceItems( )
    {
        return _insertListCandidateReferenceItems;
    }

    public void set_insertListCandidateReferenceItems( List<ReferenceItem> _insertListCandidateReferenceItems )
    {
        this._insertListCandidateReferenceItems = _insertListCandidateReferenceItems;
    }

    public static String createMessage( CompareResult result, Locale locale )
    {
        String message = "";
        int update = result.get_updateListCandidateReferenceItems( ).size( );
        int duplicate = result.get_duplicateListCandidateReferenceItems( ).size( );
        int insert = result.get_insertListCandidateReferenceItems( ).size( );

        if ( duplicate > 0 )
            message = message + "<strong>" + duplicate + "</strong> " + I18nService.getLocalizedString( INFO_REFERENCEITEM_DUPLICATE_IN_TABLE, locale )
                    + "<br>";
        if ( update > 0 )
            message = message + "<strong>" + update + "</strong> " + I18nService.getLocalizedString( INFO_REFERENCEITEM_TO_UPDATE, locale ) + " <br>";
        if ( insert > 0 )
            message = message + "<strong>" + insert + "</strong> " + I18nService.getLocalizedString( INFO_REFERENCEITEM_TO_INSERT, Locale.getDefault( ) );
        return message;
    }
}
