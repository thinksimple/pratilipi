echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | sudo tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | sudo tee -a /etc/apt/sources.list.d/webupd8team-java.list
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886

sudo apt-get update

sudo apt-get install vim
sudo apt-get install git
sudo apt-get install oracle-java7-installer
sudo apt-get install screen
# sudo apt-get install maven
sudo apt-get install ant

# Installing the logging agent
curl -sSO https://storage.googleapis.com/signals-agents/logging/google-fluentd-install.sh
sha256sum google-fluentd-install.sh
48a50746b23c1deb7ec4b5e377631e2c77b95ecada5eda2bc8986a1e04359141  google-fluentd-install.sh
sudo bash google-fluentd-install.sh

# Manually install maven. Apt-get installs older version of maven.
sudo mkdir -p /usr/local/apache-maven
cd /usr/local/apache-maven
sudo wget http://apache.go-parts.com/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
sudo tar -xzvf apache-maven-3.3.3-bin.tar.gz
cd ~

git clone -b master https://github.com/Pratilipi/pratilipi.git