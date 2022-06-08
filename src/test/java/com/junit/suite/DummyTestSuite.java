package com.junit.suite;

import com.junit.helper.ArraysTest;
import com.junit.helper.StringHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ArraysTest.class, StringHelperTest.class})
public class DummyTestSuite {

}
