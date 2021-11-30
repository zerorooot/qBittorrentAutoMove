# qBittorrentAutoMove

Move the files downloaded via qBittorrent automatically

## NOTE
Must be enabled "Remote control" (Tools->Preferences->Web UI->Web User Interface)
Must be enabled "Remote control" (Tools->Preferences->Web UI->Web User Interface)
Must be enabled "Remote control" (Tools->Preferences->Web UI->Web User Interface)

## Getting Started
### Config info
```bash
vim config.properties
```
```bash
#qBittorrent hosts . Default value is 127.0.0.1:8080 
hosts=127.0.0.1:8080
#qBittorrent username . Default value is admin
username=admin
#qBittorrent password . Default value is adminadmin
password=adminadmin
#Where to move . Default value is current working directory 
path=~/path
```
### Started
```bash
git clone https://github.com/zerorooot/qBittorrentAutoMove.git
cd qBittorrentAutoMove
mvn assembly:assembly
java -jar target/qBittorrentAutoMove-1.0-SNAPSHOT-jar-with-dependencies.jar config.properties
```
