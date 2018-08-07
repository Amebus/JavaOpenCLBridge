package configuration;

public interface ISettingsRepository
{
	IOclContextOptions getContextOptions();
	
	IOclKernelsOptions getKernelsOptions();
}
