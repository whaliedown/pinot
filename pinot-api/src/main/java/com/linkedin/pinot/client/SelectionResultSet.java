/**
 * Copyright (C) 2014-2015 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Selection result set, which contains the results of a selection query.
 */
class SelectionResultSet extends AbstractResultSet {
  private JSONArray _resultsArray;
  private JSONArray _columnsArray;

  public SelectionResultSet(JSONObject selectionResults) {
    try {
      _resultsArray = selectionResults.getJSONArray("results");
      _columnsArray = selectionResults.getJSONArray("columns");
    } catch (JSONException e) {
      throw new PinotClientException(e);
    }
  }

  @Override
  public int getRowCount() {
    return _resultsArray.length();
  }

  @Override
  public int getColumnCount() {
    return _columnsArray.length();
  }

  @Override
  public String getColumnName(int columnIndex) {
    try {
      return _columnsArray.getString(columnIndex);
    } catch (JSONException e) {
      throw new PinotClientException(e);
    }
  }

  @Override
  public String getString(int rowIndex, int columnIndex) {
    try {
      return _resultsArray.getJSONArray(rowIndex).getString(columnIndex);
    } catch (JSONException e) {
      throw new PinotClientException(e);
    }
  }

  @Override
  public int getGroupKeyLength() {
    return 0;
  }

  @Override
  public String getGroupKeyString(int rowIndex, int groupKeyColumnIndex) {
    throw new AssertionError("No group key string for selection results");
  }
}
