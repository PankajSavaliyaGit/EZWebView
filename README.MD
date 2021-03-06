Advanced Webview [![Build Status](https://travis-ci.org/jjhesk/EZWebView.svg?branch=master)](https://travis-ci.org/jjhesk/EZWebView)[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Advanced%20Webview-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2390)[ ![Download](https://api.bintray.com/packages/jjhesk/maven/ezwebviewv/images/download.svg) ](https://bintray.com/jjhesk/maven/ezwebviewv/_latestVersion)
======
Latest Version v1.4.1
=======


Feature list
- [x] handler Google SSL alert #2
- [x] no more memory leak issue in video iframe
- [x] easy web page margin fixed
- [x] easy web video implementation
- [x] easy web content display implementation
- [x] took care of life cycle issues
- [x] fantasy animation reveal fade in
- [x] ```UriFactory``` set
- [x] ```Fx9C``` set
- [x] ```In32``` set
- [x] ```CommentBoxUrl``` resolve facebook, weibo, popbee comment issues

Download for the [Sample APK][2]

For initiating a webview, simply thing to do this to do it once for all.

[Demo example code][1]

Remote Repo
```gradle
    repositories {
        maven { url "http://dl.bintray.com/jjhesk/maven" }
    }
```
Dependency
```gradle
   compile 'com.hkm.ezwebview:ezwebviewv:1.4.1'
```


Step 1.
======
please locate in the xml for the video display
```xml

                <RelativeLayout
                    android:id="@+id/framevideoplayer"
                    android:layout_width="match_parent"
                    android:layout_height="@dimen/item_height_video"
                    android:background="@color/main_background"
                    android:padding="1dp"
                    android:visibility="gone">

                    <com.hkm.ezwebview.webviewleakfix.NonLeakingWebView
                        android:id="@+id/videoplayer"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_centerInParent="true"
                        android:background="@color/main_background"
                        android:visibility="gone" />

                    <com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar               xmlns:fab="http://schemas.android.com/apk/res-auto"
                        android:id="@+id/progressloadingbarpx"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_centerInParent="true"
                        fab:mlpb_enable_circle_background="true"
                        fab:mlpb_inner_radius="18dp"
                        fab:mlpb_progress_color="@color/second_grey_bg"
                        fab:mlpb_show_arrow="false" />
                </RelativeLayout>
```
if you need to start a content display for html please also specify the xml as below:
```xml
   <RelativeLayout
                    android:id="@+id/content_article_frame"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="10dp"
                    android:layout_marginLeft="10dp"
                    android:layout_marginRight="10dp"
                    android:visibility="gone">

                    <com.hkm.ezwebview.webviewleakfix.NonLeakingWebView
                        android:id="@+id/content_block"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:visibility="gone"
                        tool:ignore="WebViewLayout" />
                </RelativeLayout>
```


### HTML content display area
=======
This is the fast simple implementation without any callbacks:

```java

  @Override
    public void onViewCreated(View v, Bundle b) {
        initBinding(v);
        final String contentc = fromFileRaw(getActivity(), R.raw.sample_html);
        try {
            Fx9C.setup_content_block_wb(this, content_article_frame, block, contentc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (bloadlistenr != null)
        //   bloadlistenr.onLoad();
    }

```

### This is the advanced implementation:

```java
  @Override
    public void onViewCreated(View v, Bundle b) {
        initBinding(v);
        final String contentc = fromFileRaw(getActivity(), R.raw.sample_html);
        try {
            Fx9C.setup_content_block_wb(this, content_article_frame, block, contentc, new HClient.Callback() {

                @Override
                public void retrieveCookie(String cookie_string) {

                }
            }, new Runnable() {
                @Override
                public void run() {
                    ViewCompat.animate(mprogressbar).alpha(0f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            mprogressbar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (bloadlistenr != null)
        //   bloadlistenr.onLoad();
    }

```

### After enhancement
=======
![](device-2015-09-02-114842.png)
![](device-2015-09-02-114926.png)

### Video embeded content display area
=======
```java
 try {
            CircleProgressBar bprogressbar = (CircleProgressBar)this.findViewById(R.id.progressloadingbarpx);
            Fx9C.setup_web_video(
                    this,
                    video_frame,
                    mVideo,
                    bprogressbar,
                    video_embed_code_webvoew,
                    new HClient.Callback() {
                        @Override
                        public void startNewActivity(String s, String s1, String s2, Context context) {

                        }

                        @Override
                        public void startFeedList(String s, Context context) {

                        }

                        @Override
                        public void openUri(String s, Context context) {

                        }

                        @Override
                        public void retrieveCookie(String s) {

                        }
                    }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
```

License
--------

    Copyright 2015 jjHESK, HKM UNITED

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/jjhesk/EZWebView/tree/master/app/src/main/java/com/hkm/ezwebviewsample
[2]: https://docs.google.com/uc?authuser=0&id=0B5hHvje_X5v3eFU0d2VIWjhuRjA&export=download
