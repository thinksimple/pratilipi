sudo su
cd ~


# Update apt-get
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | sudo tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | sudo tee -a /etc/apt/sources.list.d/webupd8team-java.list
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
apt-get update


# https://cloud.google.com/sdk/downloads#apt-get
apt-get install google-cloud-sdk
apt-get install google-cloud-sdk-app-engine-java


# Install required software
apt-get install git
apt-get install oracle-java7-installer
# apt-get install maven
apt-get install ant


# Manually install maven. Apt-get installs older version of maven.
mkdir -p /usr/local/apache-maven
cd /usr/local/apache-maven
wget http://www-eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
tar -xzvf apache-maven-3.3.9-bin.tar.gz
rm apache-maven-3.3.9-bin.tar.gz
cd ~


# Installing Google Cloud Logging agent
wget https://storage.googleapis.com/signals-agents/logging/google-fluentd-install.sh
sha256sum google-fluentd-install.sh
bash google-fluentd-install.sh
rm google-fluentd-install.sh
