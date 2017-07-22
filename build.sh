#!/bin/bash

gradle clean shadowJar
docker build . -t stat-generator