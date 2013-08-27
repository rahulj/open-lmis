-- Copyright © 2013 VillageReach.  All Rights Reserved.  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
-- If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
DROP TABLE IF EXISTS shipment_file_columns;

CREATE TABLE shipment_file_columns (
  id                        SERIAL PRIMARY KEY,
  dataFieldLabel            VARCHAR(50),
  position                  INTEGER NOT NULL,
  includedInShipmentFile    BOOLEAN NOT NULL,
  mandatory                 BOOLEAN NOT NULL,
  datePattern               VARCHAR(25),
  createdBy                 INTEGER,
  createdDate               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedBy                INTEGER,
  modifiedDate              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);