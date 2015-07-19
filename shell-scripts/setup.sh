sudo apt-get update
sudo apt-get install git
sudo apt-get install oracle-java7-installer
# sudo apt-get install maven
sudo apt-get install ant

# Manually install maven. Apt-get installs older version of maven.
sudo mkdir -p /usr/local/apache-maven
cd /usr/local/apache-maven
sudo wget http://apache.go-parts.com/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
sudo tar -xzvf apache-maven-3.3.3-bin.tar.gz

git clone -b master https://github.com/Pratilipi/pratilipi.git