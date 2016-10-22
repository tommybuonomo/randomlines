#Random Lines Background

This is a material random background animation!

![ezgif com-video-to-gif 4](https://cloud.githubusercontent.com/assets/15737675/19619747/736f629e-986e-11e6-82c9-c6dc61fd334d.gif) ![ezgif com-video-to-gif 5](https://cloud.githubusercontent.com/assets/15737675/19619800/434b1a1c-986f-11e6-9fc9-9eddf37e2869.gif)

##How to
####Gradle
```Gradle
dependencies {
    compile 'com.tbuonomo.randomlines:randomlines:1.0.1'
}
```
####In your XML layout
```Xml
<com.tbuonomo.randomlines.RandomLinesView
    android:id="@+id/random_lines_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:lineColor="@color/backgroundLinesColor"
    app:lineWidth="5dp"
    app:lineSpacing="60dp"/>
```

####Attributes
| Attribute | Description |
| --- | --- |
| lineColor | Color of the lines (by default white) |
| lineWidth | Width of the lines (by default 0.2dp) |
| lineSpacing | Spacing beetween lines (by default 50dp) |

##License
    Copyright 2016 Tommy Buonomo
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
