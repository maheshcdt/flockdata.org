/*
 * Copyright (c) 2012-2013 "Monowai Developments Limited"
 *
 * This file is part of AuditBucket.
 *
 * AuditBucket is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuditBucket is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuditBucket.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.auditbucket.test.functional;

/**
 * User: Mike Holdsworth
 * Since: 10/08/13
 */
public class Helper {
    public static String getBigJsonText(int i) {
        return "{\n" +
                "   \"trainprofiles\": [\n" +
                "        {\n" +
                "           \"name\":\"TP-" + i + "\",\n" +
                "           \"startDate\":\"20120918\",\n" +
                "           \"endDate\":\"20120924\",\n" +
                "           \"type\":\"M\",\n" +
                "           \"class\":\"UF\",\n" +
                "           \"locations\": [\n" +
                "                {\n" +
                "                   \"name\":\"PNTH\",\n" +
                "                   \"workstationDetails\": {\n" +
                "                       \"stationType\":\"ORIG\",\n" +
                "                       \"dayEnroute\": 416\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                   \"name\":\"WHRRA\",\n" +
                "                   \"workstationDetails\": {\n" +
                "                       \"stationType\":\"DEST\",\n" +
                "                       \"dayEnroute\": 0\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "           \"schedules\": [\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"MON\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"TUE\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"WED\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"THU\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"FRI\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"SAT\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"SUN\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"WHRRA\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "           \"name\":\"B56\",\n" +
                "           \"startDate\":\"20080708\",\n" +
                "           \"endDate\":\"99999999\",\n" +
                "           \"type\":\"M\",\n" +
                "           \"class\":\"EX\",\n" +
                "           \"locations\": [\n" +
                "                {\n" +
                "                   \"name\":\"PNTH\",\n" +
                "                   \"workstationDetails\": {\n" +
                "                       \"stationType\":\"ORIG\",\n" +
                "                       \"dayEnroute\": 508\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                   \"name\":\"TAUM\",\n" +
                "                   \"workstationDetails\": {\n" +
                "                       \"stationType\":\"DEST\",\n" +
                "                       \"dayEnroute\": 0\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "           \"schedules\": [\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"TUE\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"TAUM\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"WED\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"TAUM\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                   \"dayOfWeek\": {\n" +
                "                       \"day\":\"THU\"\n" +
                "                    },\n" +
                "                   \"locationTimings\": [\n" +
                "                        {\n" +
                "                           \"name\":\"PNTH\",\n" +
                "                           \"departTime\": 345\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"name\":\"TAUM\",\n" +
                "                           \"arriveTime\": 234\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "   \"pagination\": {\n" +
                "       \"total\": 9952,\n" +
                "       \"page\": 1,\n" +
                "       \"size\": 2,\n" +
                "       \"order\":\"train\"\n" +
                "    }\n" +
                "}\n" +
                " \n" +
                " ";
    }
}
