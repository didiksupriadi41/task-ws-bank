chmod 400 $FED_GP2_PEM
ssh \
  -o "StrictHostKeyChecking=no" \
  -i $FED_GP2_PEM $FED_GP2_USER@$FED_GP2_IP \
  "rm -rf ws-bank; mkdir ws-bank";

scp -r \
  -o "StrictHostKeyChecking=no" \
  -i $FED_GP2_PEM \
  * $FED_GP2_USER@$FED_GP2_IP:/home/$FED_GP2_USER/ws-bank;

ssh \
  -o "StrictHostKeyChecking=no" \
  -i $FED_GP2_PEM $FED_GP2_USER@$FED_GP2_IP \
  "cd ws-bank; mvn clean tomcat7:redeploy";
