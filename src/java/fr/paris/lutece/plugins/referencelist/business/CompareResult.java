/*
 * Copyright (c) 2002-2021, City of Paris
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
import fr.paris.lutece.portal.service.message.MessageHtml;

/**
 * This class provides instances management methods (create, find, ...) for Result of ReferenceItemPrepareImport
 */
public class CompareResult
{
    private static final String INFO_REFERENCEITEM_DUPLICATE_IN_TABLE = "referencelist.info.referenceitem.import.duplicateintable";
    private static final String INFO_REFERENCEITEM_TO_UPDATE = "referencelist.info.referenceitem.import.updated";
    private static final String INFO_REFERENCEITEM_TO_INSERT = "referencelist.info.referenceitem.import.toinsert";

    private static final String TAG_OPEN_STRONG = "<strong>";
    private static final String TAG_CLOSE_STRONG = "</strong> ";
    private static final String TAG_BREAK = "<br>";

    private List<ReferenceItem> _updateListCandidateReferenceItems;
    private List<ReferenceItem> _duplicateListCandidateReferenceItems;
    private List<ReferenceItem> _insertListCandidateReferenceItems;
    private String _messageResult = "";

    /**
     * 
     * @param insertListCandidateReferenceItems
     * @param updateListCandidateReferenceItems
     * @param duplicateListCandidateReferenceItems
     */
    public CompareResult( List<ReferenceItem> insertListCandidateReferenceItems, List<ReferenceItem> updateListCandidateReferenceItems,
            List<ReferenceItem> duplicateListCandidateReferenceItems )
    {
        _updateListCandidateReferenceItems = updateListCandidateReferenceItems;
        _duplicateListCandidateReferenceItems = duplicateListCandidateReferenceItems;
        _insertListCandidateReferenceItems = insertListCandidateReferenceItems;
    }

    public String getMessageResult( )
    {
        return _messageResult;
    }

    public void setMessageResult( String messageResult )
    {
        this._messageResult = messageResult;
    }

    public List<ReferenceItem> getUpdateListCandidateReferenceItems( )
    {
        return _updateListCandidateReferenceItems;
    }

    public List<ReferenceItem> getDuplicateListCandidateReferenceItems( )
    {
        return _duplicateListCandidateReferenceItems;
    }

    public List<ReferenceItem> getInsertListCandidateReferenceItems( )
    {
        return _insertListCandidateReferenceItems;
    }

    /**
     * Builds the summary of the comparison shown in the confirmation message: the counts in bold, one line each.
     *
     * @param locale
     *            the locale of the labels
     * @return the summary, as HTML for an argument of an admin message
     */
    public MessageHtml createMessage( Locale locale )
    {
        return MessageHtml.of( buildMessage( locale, TAG_OPEN_STRONG, TAG_CLOSE_STRONG, TAG_BREAK ) );
    }

    /**
     * Builds the summary of the comparison as plain text, for an error message of the page.
     *
     * @param locale
     *            the locale of the labels
     * @return the summary, as text
     */
    public String createText( Locale locale )
    {
        return buildMessage( locale, "", " ", " " ).trim( );
    }

    /**
     * Builds the summary of the comparison: each non-zero count followed by its label.
     *
     * @param locale
     *            the locale of the labels
     * @param strOpen
     *            written before each count
     * @param strClose
     *            written after each count
     * @param strBreak
     *            written between two lines
     * @return the summary
     */
    private String buildMessage( Locale locale, String strOpen, String strClose, String strBreak )
    {
        StringBuilder sbMessage = new StringBuilder( );
        appendLine( sbMessage, _duplicateListCandidateReferenceItems.size( ), INFO_REFERENCEITEM_DUPLICATE_IN_TABLE, locale, strOpen, strClose, strBreak );
        appendLine( sbMessage, _updateListCandidateReferenceItems.size( ), INFO_REFERENCEITEM_TO_UPDATE, locale, strOpen, strClose, strBreak );
        appendLine( sbMessage, _insertListCandidateReferenceItems.size( ), INFO_REFERENCEITEM_TO_INSERT, locale, strOpen, strClose, strBreak );
        return sbMessage.toString( );
    }

    /**
     * Appends a line of the summary when its count is not zero.
     *
     * @param sbMessage
     *            the summary
     * @param nCount
     *            the count
     * @param strLabelKey
     *            the key of the label
     * @param locale
     *            the locale of the label
     * @param strOpen
     *            written before the count
     * @param strClose
     *            written after the count
     * @param strBreak
     *            written before the line when it is not the first one
     */
    private static void appendLine( StringBuilder sbMessage, int nCount, String strLabelKey, Locale locale, String strOpen, String strClose, String strBreak )
    {
        if ( nCount > 0 )
        {
            if ( sbMessage.length( ) > 0 )
            {
                sbMessage.append( strBreak );
            }
            sbMessage.append( strOpen ).append( nCount ).append( strClose ).append( I18nService.getLocalizedString( strLabelKey, locale ) );
        }
    }
}
