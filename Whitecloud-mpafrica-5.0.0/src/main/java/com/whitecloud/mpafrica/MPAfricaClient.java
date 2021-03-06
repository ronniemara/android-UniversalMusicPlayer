/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.whitecloud.mpafrica;

import java.util.*;

import com.whitecloud.mpafrica.model.Empty;
import com.whitecloud.mpafrica.model.GenreTrackListModel;

@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = "https://bq76iu94yb.execute-api.us-east-1.amazonaws.com/beta")
public interface MPAfricaClient {


    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);

    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/track", method = "GET")
    Empty trackGet();
    
    /**
     * 
     * 
     * @return GenreTrackListModel
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/tracks", method = "GET")
    GenreTrackListModel tracksGet();
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/tracks", method = "OPTIONS")
    Empty tracksOptions();
    
}
