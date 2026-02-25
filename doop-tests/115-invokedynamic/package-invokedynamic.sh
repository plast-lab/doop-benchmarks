#!/usr/bin/env bash

../gradlew :115-invokedynamic:jar :115-invokedynamic:run
jar -uf ./build/libs/115-invokedynamic.jar InvokedynamicClass.class
