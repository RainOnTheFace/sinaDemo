package com.map.utils;


import com.example.demo.exception.SinaDemoException;
import org.apache.commons.lang.StringUtils;
import org.locationtech.jts.geom.Coordinate;

public class CoordinateUtil {


    public static Coordinate[] lonAndLatStringToCoordinates(String longAndLats, CoordType coordType) {
        if (StringUtils.isBlank(longAndLats)) {
            throw new SinaDemoException("The latitude and longitude is empty!");
        }
        Coordinate[] coordinates = null;
        try {
            String[] pointSplit = longAndLats.split(";");
            coordinates = new Coordinate[pointSplit.length];
            if (pointSplit == null || pointSplit.length < 1) {
                throw new SinaDemoException("latitude and longitude format error!");
            }
            for (int i = 0; i < pointSplit.length; i++) {
                String point = pointSplit[i];
                String[] pStr = point.split(",");
                if (pStr.length != 2) {
                    throw new SinaDemoException("latitude and longitude format error!");
                }
                double[] dCoordinates = new double[]{Double.parseDouble(pStr[0]), Double.parseDouble(pStr[1])};
                if (CoordType.gcj02ll != coordType) {
                    if (CoordType.bd09ll == coordType) {
                        dCoordinates = MapsUtils.transformBD09ToGCJ02(Double.parseDouble(pStr[0]), Double.parseDouble(pStr[1]));
                    } else {
                        dCoordinates = MapsUtils.transformWGS84ToGCJ02(Double.parseDouble(pStr[0]), Double.parseDouble(pStr[1]));
                    }
                }
                coordinates[i] = new Coordinate(dCoordinates[0], dCoordinates[1]);
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw e;
            throw new SinaDemoException("latitude and longitude format error!");
        }
        return coordinates;
    }

    public static double[] lonAndLatStringToCoordinates(Double lat, Double lon, String coordType, String defaultCoordType) {
        // 如果传入坐标和默认坐标一样，则不做转换。
        double[] dCoordinates = new double[]{lon, lat};
        if (coordType.equalsIgnoreCase(defaultCoordType)) {
            return dCoordinates;
        }

        if ("gcj02ll".equalsIgnoreCase(defaultCoordType)) {
            if ("bd09ll".equalsIgnoreCase(coordType)) {
                dCoordinates = MapsUtils.transformBD09ToGCJ02(lon, lat);
            } else if ("wgs84ll".equalsIgnoreCase(coordType)) {
                dCoordinates = MapsUtils.transformWGS84ToGCJ02(lon, lat);
            }
        } else if ("wgs84ll".equalsIgnoreCase(defaultCoordType)) {
            if ("gcj02ll".equalsIgnoreCase(coordType)) {
                dCoordinates = MapsUtils.transformGCJ02ToWGS84(lon, lat);
            } else if ("bd09ll".equalsIgnoreCase(coordType)) {
                dCoordinates = MapsUtils.transformBD09ToWGS84(lon, lat);
            }
        }

        return dCoordinates;
    }
}
