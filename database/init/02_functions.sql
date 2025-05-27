CREATE OR REPLACE FUNCTION enforce_uppercase_hex()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.hex := UPPER(NEW.hex);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER enforce_uppercase_hex_trigger
    BEFORE INSERT OR UPDATE ON public.color
    FOR EACH ROW
EXECUTE FUNCTION enforce_uppercase_hex();