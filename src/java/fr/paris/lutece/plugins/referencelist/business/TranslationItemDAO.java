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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for TranslationItem objects
 */
public final class TranslationItemDAO implements ITranslationItemDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT t.id_translation, i.id_reference_item, i.name, t.lang, t.name FROM referencelist_translation t, referencelist_item i"
            + " where t.id_reference_item = i.id_reference_item ";

    private static final String SQL_QUERY_INSERT = "INSERT INTO referencelist_translation ( id_reference_item, lang, name ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM referencelist_translation WHERE id_translation = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE referencelist_translation SET id_reference_item = ?, lang = ?, name = ? WHERE id_translation = ?";

    private static final String SQL_QUERY_SELECTALL = SQL_QUERY_SELECT + " and i.idreference = ? ORDER BY t.lang, i.name";
    private static final String SQL_QUERY_SELECTONE = SQL_QUERY_SELECT + " and t.id_translation = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( TranslationItem item, Plugin plugin )
    {
        AppLogService.info( "TranslationItemDAO -> insert" );

        AppLogService.info( "item created : idItem = " + item.getIdItem( ) );
        AppLogService.info( "item created : lang = " + item.getLang( ) );
        AppLogService.info( "item created : name = " + item.getTranslation( ) );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, item.getIdItem( ) );
        daoUtil.setString( nIndex++, item.getLang( ) );
        daoUtil.setString( nIndex++, item.getTranslation( ) );

        daoUtil.executeUpdate( );
        if ( daoUtil.nextGeneratedKey( ) )
        {
        	item.setId( daoUtil.getGeneratedKeyInt( 1 ) );
        }
        
        daoUtil.close( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TranslationItem load( int nKey, Plugin plugin )
    {
        TranslationItem item = null;

        AppLogService.info( "TranslationItemDAO -> load id = " + nKey );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTONE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
        	item = new TranslationItem( );
        	
            int nIndex = 1;

            item.setId( daoUtil.getInt( nIndex++ ) );
            item.setIdItem( daoUtil.getInt( nIndex++ ) );
            item.setName( daoUtil.getString( nIndex++ ) );
            item.setLang( daoUtil.getString( nIndex++ ) );
            item.setTranslation( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.close( );

        return item;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        AppLogService.info( "TranslationItemDAO -> delete id = " + nKey );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( TranslationItem item, Plugin plugin )
    {
        AppLogService.info( "TranslationItemDAO -> store" );

        AppLogService.info( "item modified : id = " + item.getId( ) );
        AppLogService.info( "item modified : idItem = " + item.getIdItem( ) );
        AppLogService.info( "item modified : lang = " + item.getLang( ) );
        AppLogService.info( "item modified : name = " + item.getTranslation( ) );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        
        int nIndex = 1;

        daoUtil.setInt( nIndex++, item.getIdItem( ) );
        daoUtil.setString( nIndex++, item.getLang( ) );
        daoUtil.setString( nIndex++, item.getTranslation( ) );
        daoUtil.setInt( nIndex++, item.getId( ) );

        daoUtil.executeUpdate( );
        
        daoUtil.close( );
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TranslationItem> selectTranslationItems( int idReference, Plugin plugin )
    {
        AppLogService.info( "TranslationItemDAO -> selectTranslationItems : idReference = " + idReference );

        List<TranslationItem> listTranslationItems = new ArrayList<TranslationItem>( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.setInt( 1, idReference );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            TranslationItem item = new TranslationItem( );
            int nIndex = 1;

            item.setId( daoUtil.getInt( nIndex++ ) );
            item.setIdItem( daoUtil.getInt( nIndex++ ) );
            item.setName( daoUtil.getString( nIndex++ ) );
            item.setLang( daoUtil.getString( nIndex++ ) );
            item.setTranslation( daoUtil.getString( nIndex++ ) );

            listTranslationItems.add( item );
        }

        daoUtil.close( );

        return listTranslationItems;
    }

}
