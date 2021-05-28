package com.example.junggar;

/*
{
    "code": 0,
    "message": "길찾기를 성공하였습니다.",
    "currentDateTime": "2018-12-21T14:45:34",
    "route": {
        "trafast": [
            {
                "summary": {
                    "start": {
                        "location": [
                            127.1058342,
                            37.3597078
                        ]
                    },
                    "goal": {
                        "location": [
                            129.0759853,
                            35.1794697
                        ],
                        "dir": 2
                    },
                    "distance": 382403,
                    "duration": 15372873,
                    "bbox": [
                        [
                            127.0833901,
                            35.1793188
                        ],
                        [
                            129.0817364,
                            37.3599059
                        ]
                    ],
                    "tollFare": 24500,
                    "taxiFare": 319900,
                    "fuelPrice": 46027
                },
                "path": [
                    [
                        127.1059968,
                        37.3597093
                    ],



                    [
                        129.0764276,
                        35.1795108
                    ],
                    [
                        129.0762855,
                        35.1793188
                    ]
                ],
                "section": [
                    {
                        "pointIndex": 654,
                        "pointCount": 358,
                        "distance": 22495,
                        "name": "죽양대로",
                        "congestion": 1,
                        "speed": 60
                    },
                    {
                        "pointIndex": 3059,
                        "pointCount": 565,
                        "distance": 59030,
                        "name": "낙동대로",
                        "congestion": 1,
                        "speed": 89
                    },
                    {
                        "pointIndex": 4708,
                        "pointCount": 433,
                        "distance": 23385,
                        "name": "새마을로",
                        "congestion": 1,
                        "speed": 66
                    }
                ],
                "guide": [
                    {
                        "pointIndex": 1,
                        "type": 3,
                        "instructions": "정자일로1사거리에서 '성남대로' 방면으로 우회전",
                        "distance": 21,
                        "duration": 4725
                    },
                    {
                        "pointIndex": 8,
                        "type": 3,
                        "instructions": "불정교사거리에서 '수원·용인, 미금역' 방면으로 우회전",
                        "distance": 186,
                        "duration": 42914
                    },



                    {
                        "pointIndex": 6824,
                        "type": 14,
                        "instructions": "연산교차로에서 '서면교차로, 시청·경찰청' 방면으로 오른쪽 1시 방향",
                        "distance": 910,
                        "duration": 125240
                    },
                    {
                        "pointIndex": 6842,
                        "type": 88,
                        "instructions": "목적지",
                        "distance": 895,
                        "duration": 111333
                    }
                ]
            }
        ]
    }
}

 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultPath2 {


    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    @SerializedName("route")
    public Route route;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "ResultPath{" +
                "code=" + code +
                ", message=" + message +
                "route" +route+
                '}';
    }

    public class Route {
        @SerializedName("trafast")
        @Expose
        public List<Trafast> trafast;

        public List<Trafast> getTrafast() {
            return trafast;
        }

        public void setTrafast(List<Trafast> trafast) {
            this.trafast = trafast;
        }
    }


    public class Trafast {

        @SerializedName("path")
        public List<List<Double>> path;

        @SerializedName("summary")
        public Summary summary;

        public List<List<Double>> getPath() {
            return path;
        }

        public void setPath(List<List<Double>> path) {
            this.path = path;
        }

        public Summary getSummary() {
            return summary;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }
    }



    public class Summary {

        @SerializedName("distance")
        public int distance;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }


}