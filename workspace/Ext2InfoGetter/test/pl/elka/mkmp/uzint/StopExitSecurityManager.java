package pl.elka.mkmp.uzint;

public class StopExitSecurityManager extends SecurityManager {

	@Override
	public void checkPermission(java.security.Permission perm) {
		
	}
	
	@Override
	public void checkExit(int status) {
		throw new SystemExitCallException(status);
	}
	
}
