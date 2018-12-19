# Source this script from a linux shell to setup the environment variables for the corresponding project.
pushd "$(dirname "${BASH_SOURCE:-$0}")" >/dev/null

. $PWD/scripts/environment-project

popd > /dev/null
