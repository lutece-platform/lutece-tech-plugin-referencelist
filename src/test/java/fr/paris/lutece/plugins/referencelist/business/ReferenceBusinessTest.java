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

import fr.paris.lutece.test.LuteceTestCase;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * This is the business class test for the object Reference
 */
public class ReferenceBusinessTest extends LuteceTestCase
{
    private static final String NAME1 = "Name1";
    private static final String NAME2 = "Name2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";

    @Inject
    private TestReferenceItemRemovalObserver _observer;

    /**
     * test Reference
     */
    @Test
    void testBusiness( )
    {
        // Initialize an object
        Reference reference = new Reference( );
        reference.setName( NAME1 );
        reference.setDescription( DESCRIPTION1 );

        // Create test
        ReferenceHome.create( reference );
        Reference referenceStored = ReferenceHome.findByPrimaryKey( reference.getId( ) );
        assertEquals( referenceStored.getName( ), reference.getName( ) );
        assertEquals( referenceStored.getDescription( ), reference.getDescription( ) );

        // Update test
        reference.setName( NAME2 );
        reference.setDescription( DESCRIPTION2 );
        ReferenceHome.update( reference );
        referenceStored = ReferenceHome.findByPrimaryKey( reference.getId( ) );
        assertEquals( referenceStored.getName( ), reference.getName( ) );
        assertEquals( referenceStored.getDescription( ), reference.getDescription( ) );

        // List test
        ReferenceHome.getReferencesList( );

        // Delete test
        ReferenceHome.remove( reference.getId( ) );
        referenceStored = ReferenceHome.findByPrimaryKey( reference.getId( ) );
        assertNull( referenceStored );

    }

    /**
     * Removing a whole list notifies the removal of each of its items, as removing the items one by one
     */
    @Test
    void testRemoveNotifiesTheRemovalOfTheItems( ) throws InterruptedException
    {
        Reference reference = new Reference( );
        reference.setName( "RemoveNotifies" );
        reference.setDescription( DESCRIPTION1 );
        ReferenceHome.create( reference );
        ReferenceItem item1 = createItem( reference, "x" );
        ReferenceItem item2 = createItem( reference, "y" );

        ReferenceHome.remove( reference.getId( ) );

        assertTrue( ReferenceItemHome.getReferenceItemsList( reference.getId( ) ).isEmpty( ) );
        // the events are asynchronous
        for ( int i = 0; i < 50 && !_observer.getRemovedIds( ).containsAll( java.util.List.of( item1.getId( ), item2.getId( ) ) ); i++ )
        {
            Thread.sleep( 100 );
        }
        assertTrue( _observer.getRemovedIds( ).contains( item1.getId( ) ) );
        assertTrue( _observer.getRemovedIds( ).contains( item2.getId( ) ) );
    }

    private static ReferenceItem createItem( Reference reference, String strCode )
    {
        ReferenceItem item = new ReferenceItem( );
        item.setName( strCode );
        item.setCode( strCode );
        item.setIdreference( reference.getId( ) );
        ReferenceItemHome.create( item );
        return item;
    }

}
