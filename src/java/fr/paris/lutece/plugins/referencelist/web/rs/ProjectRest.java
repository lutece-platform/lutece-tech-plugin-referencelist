/**
 * 
 */
package fr.paris.lutece.plugins.referencelist.web.rs;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.util.json.ErrorJsonResponse;
import fr.paris.lutece.util.json.JsonResponse;
import fr.paris.lutece.util.json.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Rest web service for Project Plugin
 *  @author Emile
 */
@Path ( RestConstants.BASE_PATH + "example/api/items" )
public class ProjectRest 
{
	public static final int VERSION_1 = 1;
	
	/**
	 * Get Project List 
	 * @param nVersion the API version
	 * @return the Project List
	 */
	@GET
	@Path ( "" )
	@Produces ( MediaType.APPLICATION_JSON ) 
	public Response getProjectList( )
	{
		Response response ; 
		
		response = getProjectListV1();
		
		return response;
	}
	
	/**
	 * Get Project List V1
	 * @return the Project List for V1
	 */
	public Response getProjectListV1()
	{
		List<ReferenceItem> listItems = new ArrayList<ReferenceItem> ();
		ReferenceItem item = new ReferenceItem();
		Response response ; 
		ResponseBuilder builder;
		String strJsonResponse;
		
		item.setId(1);
		item.setCode("Code1");
		item.setName("Name1");
		item.setIdreference(1);
		
		listItems.add(item);
		
		if ( listItems.isEmpty() )
		{
			builder = Response.status(Response.Status.NO_CONTENT);
			
			strJsonResponse = JsonUtil.buildJsonResponse(new JsonResponse("{}"));
			
			response = builder.entity(strJsonResponse).build(); 
		}
		else 
		{
			builder = Response.status(Response.Status.OK);
			
			strJsonResponse = JsonUtil.buildJsonResponse(new JsonResponse(listItems));
			
			response = builder.entity(strJsonResponse).build();
		}
		
		return response;
	}
}
