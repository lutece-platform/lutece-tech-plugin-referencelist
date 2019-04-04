/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for ReferenceItem objects
 */
public final class ReferenceItemDAO implements IReferenceItemDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item WHERE id_reference_item = ?";
    private static final String SQL_QUERY_SELECT_ID = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item WHERE idreference = ?";
    private static final String SQL_QUERY_SELECT_NAME = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item WHERE idreference = ? AND item_name = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO referencelist_item ( item_name, item_value, idreference ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM referencelist_item WHERE id_reference_item = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE referencelist_item SET item_name = ?, item_value = ? WHERE id_reference_item = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_reference_item FROM referencelist_item";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( ReferenceItem referenceItem, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, referenceItem.getItemName( ) );
            daoUtil.setString( nIndex++, referenceItem.getItemValue( ) );
            daoUtil.setInt( nIndex++, referenceItem.getIdreference( ) );
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                referenceItem.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceItem load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        ReferenceItem referenceItem = null;

        if ( daoUtil.next( ) )
        {
            referenceItem = new ReferenceItem( );
            int nIndex = 1;

            referenceItem.setId( daoUtil.getInt( nIndex++ ) );
            referenceItem.setItemName( daoUtil.getString( nIndex++ ) );
            referenceItem.setItemValue( daoUtil.getString( nIndex++ ) );
            referenceItem.setIdreference( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return referenceItem;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( ReferenceItem referenceItem, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setString( nIndex++, referenceItem.getItemName( ) );
        daoUtil.setString( nIndex++, referenceItem.getItemValue( ) );
        daoUtil.setInt( nIndex, referenceItem.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ReferenceItem> selectReferenceItemsList( int IdReference, Plugin plugin )
    {
        List<ReferenceItem> referenceItemList = new ArrayList<ReferenceItem>( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ID, plugin );
        daoUtil.setInt( 1, IdReference );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            ReferenceItem referenceItem = new ReferenceItem( );
            int nIndex = 1;

            referenceItem.setId( daoUtil.getInt( nIndex++ ) );
            referenceItem.setItemName( daoUtil.getString( nIndex++ ) );
            referenceItem.setItemValue( daoUtil.getString( nIndex++ ) );
            referenceItem.setIdreference( daoUtil.getInt( nIndex++ ) );

            referenceItemList.add( referenceItem );
        }

        daoUtil.free( );
        return referenceItemList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdReferenceItemsList( Plugin plugin )
    {
        List<Integer> referenceItemList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            referenceItemList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return referenceItemList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectReferenceItemsReferenceList( Plugin plugin )
    {
        ReferenceList referenceItemList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );
        while ( daoUtil.next( ) )
        {
            referenceItemList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }
        daoUtil.free( );
        return referenceItemList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceItem loadReferenceItemByName( int nIdReference, String sItemName, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_NAME, plugin );
        daoUtil.setInt( 1, nIdReference );
        daoUtil.setString( 2, sItemName );

        daoUtil.executeQuery( );
        ReferenceItem referenceItem = null;
        if ( daoUtil.next( ) )
        {
            referenceItem = new ReferenceItem( );
            int nIndex = 1;
            referenceItem.setId( daoUtil.getInt( nIndex++ ) );
            referenceItem.setItemName( daoUtil.getString( nIndex++ ) );
            referenceItem.setItemValue( daoUtil.getString( nIndex++ ) );
            referenceItem.setIdreference( daoUtil.getInt( nIndex++ ) );
        }
        daoUtil.free( );
        return referenceItem;
    }
}
