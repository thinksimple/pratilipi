cd ~


# Update apt-get
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | sudo tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | sudo tee -a /etc/apt/sources.list.d/webupd8team-java.list
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
sudo apt-get update


# Install required software
sudo apt-get install vim
sudo apt-get install git
sudo apt-get install oracle-java7-installer
sudo apt-get install screen
# sudo apt-get install maven
sudo apt-get install ant


# Manually install maven. Apt-get installs older version of maven.
sudo mkdir -p /usr/local/apache-maven
cd /usr/local/apache-maven
sudo wget http://apache.go-parts.com/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
sudo tar -xzvf apache-maven-3.3.3-bin.tar.gz
rm apache-maven-3.3.3-bin.tar.gz
cd ~


# Installing Google Cloud Logging agent
sudo wget https://storage.googleapis.com/signals-agents/logging/google-fluentd-install.sh
sha256sum google-fluentd-install.sh
sudo bash google-fluentd-install.sh
rm google-fluentd-install.sh


# Cloning git repository for prod-pratilipi/default
sudo mkdir -p ~/prod
cd ~/prod
sudo git clone -b release-prod https://github.com/Pratilipi/pratilipi.git

# Cloning git repository for prod-pratilipi/api
sudo mkdir -p ~/api
cd ~/api
sudo git clone -b release-api https://github.com/Pratilipi/pratilipi.git

# Cloning git repository for prod-pratilipi/android
sudo mkdir -p ~/android
cd ~/android
sudo git clone -b release-android https://github.com/Pratilipi/pratilipi.git

# Cloning git repository for prod-pratilipi/worker
sudo mkdir -p ~/worker
cd ~/worker
sudo git clone -b release-worker https://github.com/Pratilipi/pratilipi.git

# Cloning git repository for prod-pratilipi/gamma & devo-pratilipi/*
sudo mkdir -p ~/test
cd ~/test
sudo git clone -b master https://github.com/Pratilipi/pratilipi.git
