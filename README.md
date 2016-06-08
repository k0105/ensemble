Ensemble Service
=======================

## Introduction
This ensemble provides a way to query multiple QA systems and merge their results. There are two Gradle tasks to
either launch a RESTful interface (./gradlew runRestBackend) or one based on Apache Thrift (./gradlew runThriftBackend).

## To build
- Extract dependencies into a folder `lib` (javax.persistence.jar jaws-bin.jar minimal-json-0.9.4.jar and simmetrics_jar_v1_6_2_d07_02_07.jar and folder thrift containing Thrift 0.9.3)
- Recreate the gradlew configuration with `gradle wrapper`
- To run the code just execute the task runRestBackend or runThriftBackend with the local addresses (default assumes Docker links), e.g. `./gradlewsemble.webqaurl="http://127.0.0.1:4000" -Dde.rwthaachen.ensemble.webqaurl="http://127.0.0.1:4567" -Dde.rwthaachen.ensemble.rakeurl="http://127.0.0.1:4124" -Dde.rwthaachen.ensemble.bluemixurl="http://127.0.0.1:9093"`

### ASLv2
    Ensemble Service
    Copyright 2016 RWTH Aachen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
