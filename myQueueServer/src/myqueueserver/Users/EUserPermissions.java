package myqueueserver.Users;

import java.io.Serializable;

/**
 *
 * @author Nikos Siatras
 */
public enum EUserPermissions implements Serializable
{

    All, CreateUsers, DropUsers, CreateQueues, DropQueues
}
