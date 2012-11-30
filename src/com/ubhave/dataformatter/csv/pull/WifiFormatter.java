/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

This library was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.dataformatter.csv.pull;

import java.util.ArrayList;

import android.net.wifi.ScanResult;

import com.ubhave.dataformatter.csv.CSVFormatter;
import com.ubhave.sensormanager.config.SensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pullsensor.WifiData;

public class WifiFormatter extends CSVFormatter
{
	private final static String SSID = "ssid";
	private final static String BSSID = "bssid";
	private final static String CAPABILITIES = "capabilities";
	private final static String LEVEL = "level";
	private final static String FREQUENCY = "frequency";

	private final static String UNAVAILABLE = "unavailable";
	private final static String SENSE_CYCLES = "senseCycles";

	@Override
	protected void addSensorSpecificData(StringBuilder builder, SensorData data)
	{
		WifiData wifiData = (WifiData) data;
		ArrayList<ScanResult> results = wifiData.getWifiScanData();
		if (results != null)
		{
			for (ScanResult result : results)
			{
				StringBuilder scanCSV = new StringBuilder();
				scanCSV.append("{"+result.SSID);
				scanCSV.append(";"+result.BSSID);
				scanCSV.append(";"+result.capabilities);
				scanCSV.append(";"+result.level);
				scanCSV.append(";"+result.frequency);
				scanCSV.append("}");
				builder.append(","+scanCSV);
			}
		}
		else
		{
			builder.append(UNAVAILABLE);
		}
	}

	@Override
	protected void addSensorSpecificConfig(StringBuilder builder, SensorConfig config)
	{
		builder.append(","+config.getParameter(SensorConfig.NUMBER_OF_SENSE_CYCLES));
	}

	@Override
	protected void addSensorSpecificHeaders(StringBuilder builder)
	{
		builder.append(","+SENSE_CYCLES);
		builder.append(",{"+SSID);
		builder.append(";"+BSSID);
		builder.append(";"+CAPABILITIES);
		builder.append(";"+LEVEL);
		builder.append(";"+FREQUENCY+"}");
	}

}