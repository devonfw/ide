rem This batch sets up devonfw ide for microservices development providing with containerizing and orchestration tools such as Docker and Kubernetes on WSL.
cd %USERPROFILE%

rem  Check and proceed only if WSL is enabled.
for /F "delims=" %%I in ('dism /online /get-featureinfo /featurename:Microsoft-Windows-Subsystem-Linux ^| findstr /i "State"') do (set  WSL_STATE=%%I)
echo.%WSL_STATE%|findstr /C:"Enabled" >nul 2>&1
if not errorlevel 1 (
    echo WSL ENABLED
) else (
    echo WSL DISABLED. EXITING. Please enable WSL to proceed with devonfw IDE setup. 
    goto :eof
)

rem  Check and proceed only if there are any linux distributions available.
for /F "delims=" %%I in ('wsl cat /proc/version ^| findstr /i "Linux version"') do (set  LINUX_DISTRIBUTIONS=%%I)
echo.%LINUX_DISTRIBUTIONS%|findstr /C:"Linux version" >nul 2>&1
if errorlevel 1 (
    echo No Linux distributions found. Exiting. Please install any linux distribution to proceed with devonfw IDE setup.
    goto :eof
)

rem INSTALL KUBERNETES IF NOT ALREADY INSTALLED
setlocal EnableDelayedExpansion
set KUBERNETES_INSTALLED=''
for /f "tokens=*" %%i in ('wsl minikube version ^| findstr /i "minikube version"') do (set KUBERNETES_INSTALLED=%%i)
echo.%KUBERNETES_INSTALLED%|findstr /C:"minikube version" >nul 2>&1
if errorlevel 1 (
    echo Installing Minikube(single node kubernetes cluster) and Kubectl...
    wsl curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg ^| sudo apt-key add -
    wsl sudo touch /etc/apt/sources.list.d/kubernetes.list 
    wsl echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" ^| sudo tee -a /etc/apt/sources.list.d/kubernetes.list
    wsl sudo apt-get update
    wsl sudo apt-get install -y kubectl

    wsl curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.10.1/minikube-linux-amd64 minikube
    wsl sudo mv minikube /usr/local/bin/
    wsl chmod +x /usr/local/bin/minikube
    wsl minikube version
) 
if errorlevel 0 (
    echo Kubernetes already installed. Skipping installation. 
    wsl minikube version
)
endlocal

rem INSTALL DOCKER IF NOT ALREADY INSTALLED
setlocal EnableDelayedExpansion
set DOCKER_INSTALLED=''
for /F "delims=" %%I in ('wsl docker --version ^| findstr /i "Docker version"') do (SET DOCKER_INSTALLED=%%I)
echo.%DOCKER_INSTALLED%|findstr /C:"Docker version" >nul 2>&1
if errorlevel 1 (
    echo Docker not installed. Installing docker now...
    wsl sudo apt-get update -y
    wsl sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common
    wsl curl -fsSL https://download.docker.com/linux/ubuntu/gpg ^| sudo apt-key add -
    wsl sudo apt-key fingerprint 0EBFCD88
    wsl sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    wsl sudo apt-get update -y
    wsl sudo apt-get install -y docker-ce
    wsl sudo usermod -aG docker $USER
    echo Docker installed successfully.
    wsl docker --version
)
if errorlevel 0 (
    echo Docker already installed. Skipping installation. 
    wsl docker --version
)
endlocal


goto :eof