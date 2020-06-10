![](https://dev.lutece.paris.fr/jenkins/buildStatus/icon?job=tech-plugin-referencelist-deploy)
[![Alerte](https://dev.lutece.paris.fr/sonar/api/project_badges/measure?project=fr.paris.lutece.plugins%3Aplugin-referencelist&metric=alert_status)](https://dev.lutece.paris.fr/sonar/dashboard?id=fr.paris.lutece.plugins%3Aplugin-referencelist)
[![Line of code](https://dev.lutece.paris.fr/sonar/api/project_badges/measure?project=fr.paris.lutece.plugins%3Aplugin-referencelist&metric=ncloc)](https://dev.lutece.paris.fr/sonar/dashboard?id=fr.paris.lutece.plugins%3Aplugin-referencelist)
[![Coverage](https://dev.lutece.paris.fr/sonar/api/project_badges/measure?project=fr.paris.lutece.plugins%3Aplugin-referencelist&metric=coverage)](https://dev.lutece.paris.fr/sonar/dashboard?id=fr.paris.lutece.plugins%3Aplugin-referencelist)

# Plugin referencelist

## Introduction

**referencelist** plugin is commited to provide reference data management to every LUTECE site built with it.

It provides a LUTECE Back Office menu to manage pairs of "key/value" data like properties files. 

Those data can be used by another plugin, for example in select combo lists, without redelivering the site.

## Configuration

Activate the **referencelist** plugin through LUTECE Back Office.

Create one or more references (ex : "title") with their items ("title.mister" with "Mr", "title.madam" with "Mrs",...). 

Each item can also have its translated values. 

## Usage

A dedicated menu in LUTECE Back Office enables to create/modify/delete data to be used by the LUTECE site.

After configuration of a reference list, the items list can be obtained through the site code by :

	import fr.paris.lutece.plugins.referencelist.service.ReferenceListService;
	import fr.paris.lutece.util.ReferenceList;

	ReferenceList listTitles = ReferenceListService.getInstance().getReferenceList("title", getLocale());

If the translated value corresponding to the local doesn't still exist, the item default value shall be returned, like for the simpler invokation :

	ReferenceList listTitles = ReferenceListService.getInstance().getReferenceList("title");

[Maven documentation and reports](https://dev.lutece.paris.fr/plugins/plugin-referencelist/)