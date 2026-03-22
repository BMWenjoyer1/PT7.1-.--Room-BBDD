# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

# For Android applications, there is a special case handling for the
# classes that are referenced in the AndroidManifest.xml It is assumed that
# these classes are accessed, even if the code is not.

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.Fragment

# Room
-keepclasseswithmembers class * {
    @androidx.room.* <methods>;
}

# Keep data classes with Room
-keep class com.example.tasques.data.entity.** { *; }
-keep class com.example.tasques.data.dao.** { *; }

# Keep all public classes by default
-keep public class com.example.tasques.** { *; }

# Removes some boilerplate
-dontwarn com.google.errorprone.annotations.*

# Lifetime stats
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends android.preference.Preference

-renamesourcefileattribute SourceFile
