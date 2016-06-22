
package com.admin.app0612;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * TODO : 合并为一个函数处理
 */
public class RestClient {
    private static AsyncHttpClient mClient = new AsyncHttpClient();
    private static RestClient mInstance = new RestClient();


    public static ObjectMapper objectMapper = new ObjectMapper();

    private RestClient() {
    }

    public static RestClient getInstance() {
        if (mInstance == null) {
            mInstance = new RestClient();
        }
        return mInstance;
    }

    private void get(String url, RequestParams params,
                     AsyncHttpResponseHandler responseHandler) {
        mClient.get(getUrl(url), params, responseHandler);
    }

    private void get(Context context, String url, HttpEntity entity,
                     String contentType, AsyncHttpResponseHandler responseHandler) {
        mClient.get(context, getUrl(url), entity, contentType, responseHandler);
    }

    private void put(String url, RequestParams params,
                     AsyncHttpResponseHandler responseHandler) {
        mClient.put(getUrl(url), params, responseHandler);
    }

    private void post(Context context, String url, HttpEntity entity,
                      String contentType, AsyncHttpResponseHandler responseHandler) {
        mClient.post(context, getUrl(url), entity, contentType, responseHandler);
    }

    private void patch(Context context, String url, HttpEntity entity,
                       String contentType, AsyncHttpResponseHandler responseHandler) {
        mClient.patch(context, getUrl(url), entity, contentType, responseHandler);
    }

    private void delete(String url, RequestParams params,
                        AsyncHttpResponseHandler responseHandler) {
        mClient.delete(getUrl(url), params, responseHandler);
    }

    public String getUrl(String uri) {
        return Constants.Request.BASE_URL + uri;
    }

    public void getUserList(final Handler handler) {
        get(Constants.Request.URL_USERS, null, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                handler.sendMessage(Message.obtain(handler, Constants.MsgId.USER_LIST, null));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                Log.i("info", "GET USERLIST******onSuccess=" + statusCode);
                try {
                    UserEntity[] users = objectMapper.readValue(response.toString(), UserEntity[].class);
                    List<UserEntity> userList = Arrays.asList(users);
                    Log.i("info", "userList=" + userList);
                    handler.sendMessage(Message.obtain(handler, Constants.MsgId.USER_LIST, userList));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void queryUser(final Handler handler, final String username) {

        //将数据转化为json
        JSONObject user = new JSONObject();
        try {
            user.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("info", "QUERY******=" + user);
        //将json数据放入参数entity中
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(user.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        get(null, Constants.Request.URL_USERS, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("info", "QUERY******onFailure=" + statusCode);
                handler.sendMessage(Message.obtain(handler, Constants.MsgId.QUERY_USER, null));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                Log.i("info", "QUERY******onSuccess=" + response);
                try {
                    UserEntity[] users = objectMapper.readValue(response.toString(), UserEntity[].class);
                    List<UserEntity> user = Arrays.asList(users);
                    Log.i("info", "user=" + user);
                    handler.sendMessage(Message.obtain(handler, Constants.MsgId.QUERY_USER, user));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void addUser(final Handler handler, final String username, final String pin) {

        //将数据转化为json
        JSONObject user = new JSONObject();
        try {
            user.put("username", username);
            user.put("pin", pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //将json数据放入参数entity中
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(user.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        post(null, Constants.Request.URL_USERS, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.i("info", "ADD******onFailure======" + statusCode);
                handler.sendMessage(Message.obtain(handler, Constants.MsgId.ADD_USER, null));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.i("info", "ADD******onSuccess=" + statusCode);

                UserEntity newUser = new UserEntity();
                newUser.username = username;
                newUser.pin = pin;

                handler.sendMessage(Message.obtain(handler, Constants.MsgId.ADD_USER, newUser));
            }
        });
    }

    public void patchUser(final Handler handler, final String username, final String pin) {

        //将数据转化为json
        JSONObject user = new JSONObject();
        try {
            user.put("pin", pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //将json数据放入参数entity中
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(user.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //修改URL为
        String patchUrl = Constants.Request.URL_USERS + "?username=" + username;

        patch(null, patchUrl, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.i("info", "PATCH******onFailure======" + statusCode);
                handler.sendMessage(Message.obtain(handler, Constants.MsgId.PATCH_USER, null));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.i("info", "PATCH******onSuccess=" + statusCode);

                UserEntity newUser = new UserEntity();
                newUser.username = username;
                newUser.pin = pin;

                handler.sendMessage(Message.obtain(handler, Constants.MsgId.PATCH_USER, newUser));
            }
        });
    }

    public void deleteUser(final Handler handler, final int itemIndex, final String deleteUser) {
        delete(Constants.Request.URL_USERS, new RequestParams("username", deleteUser), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.i("info", "DEL******onSuccess=" + statusCode);
                handler.sendMessage(Message.obtain(handler, Constants.MsgId.DEL_USER, itemIndex));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.i("info", "DEL******onFailure=" + statusCode);
                handler.sendMessage(Message.obtain(handler, Constants.MsgId.DEL_USER, null));
            }
        });
    }

}
