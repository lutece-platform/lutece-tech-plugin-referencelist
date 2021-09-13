package fr.paris.lutece.plugins.referencelist.service;

import java.util.List;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 *  Service that calls the {@link IReferenceItemListener}
 */
public class ReferenceItemListenerService
{
    private static final ReferenceItemListenerService INSTANCE = new ReferenceItemListenerService( );
    
    private ReferenceItemListenerService( )
    {
    }
    
    public static ReferenceItemListenerService getInstance( )
    {
        return INSTANCE;
    }
    
    /**
     * Called when a {@link ReferenceItem} is added
     * @param item
     */
    public void fireAddEvent( ReferenceItem item )
    {
        new Thread( ( ) -> {
            List<IReferenceItemListener> listeners = SpringContextService.getBeansOfType( IReferenceItemListener.class );
            for ( IReferenceItemListener listener : listeners )
            {
                listener.addReferenceItem( item );
            }
        }).start( );
    }
    
    /**
     * Called when a {@link ReferenceItem} is deleted
     * @param item
     */
    public void fireDeleteEvent( ReferenceItem item )
    {
        new Thread( ( ) -> {
            List<IReferenceItemListener> listeners = SpringContextService.getBeansOfType( IReferenceItemListener.class );
            for ( IReferenceItemListener listener : listeners )
            {
                listener.removeReferenceItem( item );
            }
        }).start( );
    }
    
    /**
     * Called when a {@link ReferenceItem} is updated
     * @param item
     */
    public void fireUpdateEvent( ReferenceItem item )
    {
        new Thread( ( ) -> {
            List<IReferenceItemListener> listeners = SpringContextService.getBeansOfType( IReferenceItemListener.class );
            for ( IReferenceItemListener listener : listeners )
            {
                listener.updateReferenceItem( item );
            }
        }).start( );
    }
}
