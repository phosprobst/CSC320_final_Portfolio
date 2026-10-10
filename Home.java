import java.util.Locale;

/** Represents one home record; removal clears the record for reuse. */
public class Home {
    private int square_feet;
    private String address;
    private String city;
    private String state;
    private int zip_code;
    private String Model_name;
    private String sale_status;
    private boolean populated;

    public Home(int squareFeet, String address, String city, String state,
                int zipCode, String modelName, String saleStatus) {
        try {
            String result = addHome(squareFeet, address, city, state,
                    zipCode, modelName, saleStatus);
            if (!populated) {
                throw new IllegalArgumentException(result);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Home construction failed: " + e.getMessage(), e);
        }
    }

    public String addHome(int squareFeet, String address, String city,
                          String state, int zipCode, String modelName,
                          String saleStatus) {
        try {
            if (populated) {
                throw new IllegalStateException("Remove the current home first.");
            }
            if (squareFeet <= 0 || zipCode < 0 || zipCode > 99999) {
                throw new IllegalArgumentException(
                        "Square feet must be positive; ZIP must be 0 to 99999.");
            }
            if (address == null || address.trim().isEmpty()
                    || city == null || city.trim().isEmpty()
                    || state == null || !state.trim().matches("[a-zA-Z]{2}")
                    || modelName == null || modelName.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Supply address, city, model, and a two-letter state.");
            }
            String normalized = saleStatus == null ? ""
                    : saleStatus.trim().toLowerCase(Locale.ROOT);
            if (!normalized.equals("sold") && !normalized.equals("available")
                    && !normalized.equals("under contract")) {
                throw new IllegalArgumentException("Invalid sale status.");
            }
            // Commit only after all validation succeeds.
            this.square_feet = squareFeet;
            this.address = address.trim();
            this.city = city.trim();
            this.state = state.trim().toUpperCase(Locale.ROOT);
            this.zip_code = zipCode;
            this.Model_name = modelName.trim();
            this.sale_status = normalized;
            populated = true;
            return "Home added successfully.";
        } catch (Exception e) {
            return "Home addition failed: " + e.getMessage();
        }
    }

    public String removeHome() {
        try {
            if (!populated) {
                throw new IllegalStateException("No home is stored.");
            }
            square_feet = 0;
            address = "";
            city = "";
            state = "";
            zip_code = 0;
            Model_name = "";
            sale_status = "";
            populated = false;
            return "Home removed successfully.";
        } catch (Exception e) {
            return "Home removal failed: " + e.getMessage();
        }
    }

    public String updateHome(String newSaleStatus) {
        try {
            if (!populated) {
                throw new IllegalStateException("No home is stored.");
            }
            String normalized = newSaleStatus == null ? ""
                    : newSaleStatus.trim().toLowerCase(Locale.ROOT);
            if (!normalized.equals("sold") && !normalized.equals("available")
                    && !normalized.equals("under contract")) {
                throw new IllegalArgumentException("Invalid sale status.");
            }
            sale_status = normalized;
            return "Home updated successfully.";
        } catch (Exception e) {
            return "Home update failed: " + e.getMessage();
        }
    }

    // Listing is the array-returning exception to message-returning methods.
    public String[] listHome() {
        try {
            if (!populated) {
                throw new IllegalStateException("No home is stored.");
            }
            return new String[] {
                "Square feet: " + square_feet,
                "Address: " + address,
                "City: " + city,
                "State: " + state,
                String.format(Locale.ROOT, "ZIP code: %05d", zip_code),
                "Model name: " + Model_name,
                "Sale status: " + sale_status
            };
        } catch (Exception e) {
            return new String[] {"Home listing failed: " + e.getMessage()};
        }
    }
}
