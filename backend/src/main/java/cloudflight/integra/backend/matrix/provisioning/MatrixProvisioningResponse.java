package cloudflight.integra.backend.matrix.provisioning;

public record MatrixProvisioningResponse(boolean provisioned, String message) {
    public static MatrixProvisioningResponse ok() {
        return new MatrixProvisioningResponse(true, null);
    }

    public static MatrixProvisioningResponse warn(String message) {
        return new MatrixProvisioningResponse(false, message);
    }
}
