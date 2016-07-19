package net.africahomepage.mp3africa.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MusicPlayerActivityUITest {

    @Rule
    public ActivityTestRule<MusicPlayerActivity> mActivityRule = new ActivityTestRule<>(MusicPlayerActivity.class);
}
