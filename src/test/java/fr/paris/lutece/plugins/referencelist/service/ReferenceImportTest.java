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

package fr.paris.lutece.plugins.referencelist.service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import fr.paris.lutece.plugins.referencelist.business.CompareResult;
import fr.paris.lutece.plugins.referencelist.business.Reference;
import fr.paris.lutece.plugins.referencelist.business.ReferenceHome;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;
import fr.paris.lutece.plugins.referencelist.business.ReferenceItemHome;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;

/**
 * This is the business class test for the object ReferenceItem
 */
public class ReferenceImportTest extends LuteceTestCase {

        private static final String FILE1 = "File.csv";
        private static final String FILE2 = "File.test";
        private static final String FILE3 = "File.";
        private static final String FILE4 = "File";
        private static final String FILE5 = ".";
        private static final String FILE6 = "";

        private static final String NAME1 = "Name1";
        private static final String NAME2 = "Name2";
        private static final String DESCRIPTION1 = "Description1";
        private static final String DESCRIPTION2 = "Description2";

        private static final long MAX_SIZE = 10000000;
        private static final long MIN_SIZE = 8;
        private static final long SIZE = 100;

        // insert
        String CSVInsert = "pays;iso\nFrance;fr"; // 1 insert

        // duplicate in Base
        String CSVDuplicateOnBase = "pays;iso\nFrance;fr"; // 0 insert

        // update
        String CSVUpdate = "pays;iso\nFrance;fra"; // 1 update
        String CSVNotUpdate = "pays;iso\nFrance;fr"; // 0 update

        // errors
        // num of col
        String CSVNumOfCol = "pays;iso\nFrance;fr;Belgique;"; // 0 insert
        // duplicate in file
        String CSVDuplicateInFile = "pays;iso\nFrance;fr\nFrance;fr"; // 1 insert

        public AdminUser CreateAdminUser() {
                AdminUser user = AdminUserHome.findUserByLogin("admin");
                user.setRoles(AdminUserHome.getRolesListForUser(user.getUserId()));
                user.setRights(AdminUserHome.getRightsListForUser(user.getUserId()));
                return user;
        }

        AdminUser adminUser = CreateAdminUser();

        /**
         * test isImportable
         * 
         */
        public void testBusiness() {

                // Initialize a reference
                Reference reference = new Reference();
                reference.setName(NAME1);
                reference.setDescription(DESCRIPTION1);

                // Create Reference for next steps.
                ReferenceHome.create(reference);
                Reference referenceStored = ReferenceHome.findByPrimaryKey(reference.getId());
                // Id
                int referenceStoredId = reference.getId();

                /**
                 * test isImportableCSVFile
                 * 
                 */
                boolean testName1 = ReferenceItemPrepareImport.isImportableCSVFile(FILE1, SIZE);
                assertTrue(testName1);
                boolean testName2 = ReferenceItemPrepareImport.isImportableCSVFile(FILE2, SIZE);
                assertFalse(testName2);
                boolean testName3 = ReferenceItemPrepareImport.isImportableCSVFile(FILE3, SIZE);
                assertFalse(testName3);
                boolean testName4 = ReferenceItemPrepareImport.isImportableCSVFile(FILE4, SIZE);
                assertFalse(testName4);
                boolean testName5 = ReferenceItemPrepareImport.isImportableCSVFile(FILE5, SIZE);
                assertFalse(testName5);
                boolean testName6 = ReferenceItemPrepareImport.isImportableCSVFile(FILE6, SIZE);
                assertFalse(testName6);

                /**
                 * test isErrorInCSVFile
                 * 
                 */

                String testCSVInsert = ReferenceItemPrepareImport
                                .isErrorInCSVFile(new ByteArrayInputStream(CSVInsert.getBytes(StandardCharsets.UTF_8)));
                assertNull(testCSVInsert);
                String testCSVNumOfCol = ReferenceItemPrepareImport.isErrorInCSVFile(
                                new ByteArrayInputStream(CSVNumOfCol.getBytes(StandardCharsets.UTF_8)));
                assertNotNull(testCSVNumOfCol);
                String testCSVDuplicateInFile = ReferenceItemPrepareImport.isErrorInCSVFile(
                                new ByteArrayInputStream(CSVDuplicateInFile.getBytes(StandardCharsets.UTF_8)));
                assertNotNull(testCSVDuplicateInFile);

                /**
                 * test findCandidateItems
                 * 
                 */
                List<ReferenceItem> testListInsert = ReferenceItemPrepareImport.findCandidateItems(
                                new ByteArrayInputStream(CSVInsert.getBytes(StandardCharsets.UTF_8)),
                                referenceStoredId);
                assertTrue(testListInsert.size() == 1);

                List<ReferenceItem> testListDuplicate = ReferenceItemPrepareImport.findCandidateItems(
                                new ByteArrayInputStream(CSVDuplicateInFile.getBytes(StandardCharsets.UTF_8)),
                                referenceStoredId);
                assertTrue(testListDuplicate.size() == 1);

                /**
                 * test compareReferenceItems
                 * 
                 */

                // insert
                CompareResult compareReferenceItems = ReferenceItemHome.compareReferenceItems(testListInsert,
                                referenceStoredId);
                List<ReferenceItem> insertReferenceItems = compareReferenceItems
                                .get_insertListCandidateReferenceItems();
                assertTrue(insertReferenceItems.size() == 1);

                // do insert import;
                boolean InsertImport = ReferenceImport.doImportCSV(compareReferenceItems, referenceStoredId, adminUser);
                assertTrue(InsertImport);

                // update;
                List<ReferenceItem> testListUpdate = ReferenceItemPrepareImport.findCandidateItems(
                                new ByteArrayInputStream(CSVUpdate.getBytes(StandardCharsets.UTF_8)),
                                referenceStoredId);
                CompareResult compareReferenceItems1 = ReferenceItemHome.compareReferenceItems(testListUpdate,
                                referenceStoredId);
                List<ReferenceItem> updateReferenceItems = compareReferenceItems1
                                .get_updateListCandidateReferenceItems();
                assertTrue(updateReferenceItems.size() == 1);

                // do update import;
                boolean UpdateImport = ReferenceImport.doImportCSV(compareReferenceItems1, referenceStoredId,
                                adminUser);
                assertTrue(UpdateImport);

        }

}