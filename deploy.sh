rsync \
  -av -e "ssh -l $FED_GP2_USER -i $FED_GP2_PEM" \
  --progress * $FED_GP2_IP:/home/$FED_GP2_USER/ws-bank;

ssh \
  -i $FED_GP2_PEM $FED_GP2_USER@$FED_GP2_IP \
  "cd ws-bank; mvn clean tomcat7:redeploy";
