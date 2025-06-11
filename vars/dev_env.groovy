def call(){
  
  def envvars = [:]
  node {
  withCredentials([
    string(credentialsId: 'image-tag', variable: 'image-tag'), 
    string(credentialsId: 'backend-image-uri', variable: 'backend-image-uri'), 
    string(credentialsId: 'frotnend-image-uri', variable: 'frotnend-image-uri'), 
    string(credentialsId: 'dev_ssh_ip', variable: 'dev_ssh_ip'),
    sshUserPrivateKey(credentialsId: 'd0e39f12-5b65-418a-8262-6a41e75e109e', keyFileVariable: 'ssh_key', usernameVariable: 'ssh_user')
  ]) {
  envvars = [
    image_tag : image-tag,
    backend_image_uri : backend-image-uri,
    frontend_image_uri : frontend-image-uri,
    ssh_ip : dev_ssh_ip,
    ssh_key : ssh_key,
    ssh_user : ssh_user
    ]
  }
  }
  return envvars
}
    
    
