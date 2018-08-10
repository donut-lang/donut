#! /bin/bash

cat > src/main/resources/build.properties <<END
buildAt=$(date)
version=$(git log -1 --pretty=format:"[%ad] %h %an : %s")
END