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
package fr.paris.lutece.plugins.referencelist.web.rs;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemHome;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.util.json.JsonResponse;
import fr.paris.lutece.util.json.JsonUtil;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Rest web service for ReferenceList Plugin "referenceitems/list/reference/{idReference}"
 * @author Emile 
 */
@Path ( RestConstants.BASE_PATH + "referenceitems/list" )
public class ReferenceItemRest 
{
	
	/**
	 * Get Reference Item List 
	 * @param idReference the Reference Id
	 * @return the Items List
	 */
	@GET
	@Path ( "reference/{idReference}" )
	@Produces ( MediaType.APPLICATION_JSON ) 
	public Response getReferenceItemList(@PathParam ( "idReference" ) Integer idReference)
	{
		Response response ; 
		ResponseBuilder builder;
		String strJsonResponse;
		
		List<ReferenceItem> listItems = ReferenceItemHome.getReferenceItemsList( idReference );
		
		if ( listItems.isEmpty() )
		{
			builder = Response.status(Response.Status.NO_CONTENT);
			
			strJsonResponse = JsonUtil.buildJsonResponse(new JsonResponse("{}"));
		}
		else 
		{
			builder = Response.status(Response.Status.OK);
			
			strJsonResponse = JsonUtil.buildJsonResponse(new JsonResponse(listItems));						
		}
		
		response = builder.entity(strJsonResponse).build();
		
		return response;
	}
}
