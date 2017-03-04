package com.pratilipi.api.impl.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/test/bigquery" )
public class BigQueryTestApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( BigQueryTestApi.class.getName() );

	@Get
	public static GenericResponse get( GenericRequest request ) {
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
		String datasetName = "new_dataset";
		Dataset dataset = null;
		DatasetInfo datasetInfo = DatasetInfo.newBuilder( datasetName ).build();
		dataset = bigquery.create( datasetInfo );
		logger.log( Level.INFO, "Dataset" + dataset.getDatasetId().getDataset() + " created." );
		return new GenericResponse();
	}

}