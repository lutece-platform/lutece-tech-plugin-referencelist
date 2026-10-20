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

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.test.LuteceTestCase;

/**
 * Tests the summary of an import comparison, given as HTML to the confirmation message and as text to an error message.
 */
public class CompareResultTest extends LuteceTestCase
{
    /**
     * The HTML summary holds the counts in bold, one line each; the text summary holds no tag.
     */
    @Test
    void testSummary( )
    {
        List<ReferenceItem> listTwo = List.of( new ReferenceItem( ), new ReferenceItem( ) );
        CompareResult result = new CompareResult( listTwo, List.of( new ReferenceItem( ) ), Collections.emptyList( ) );

        String strHtml = result.createMessage( Locale.ENGLISH ).getHtml( );
        assertTrue( strHtml.startsWith( "<strong>1</strong> " ), strHtml );
        assertTrue( strHtml.contains( "<br><strong>2</strong> " ), strHtml );

        String strText = result.createText( Locale.ENGLISH );
        assertTrue( strText.startsWith( "1 " ), strText );
        assertTrue( strText.contains( " 2 " ), strText );
        assertFalse( strText.contains( "<" ), strText );
    }

    /**
     * No count, no summary.
     */
    @Test
    void testEmptySummary( )
    {
        CompareResult result = new CompareResult( Collections.emptyList( ), Collections.emptyList( ), Collections.emptyList( ) );
        assertEquals( "", result.createMessage( Locale.ENGLISH ).getHtml( ) );
        assertEquals( "", result.createText( Locale.ENGLISH ) );
    }
}
