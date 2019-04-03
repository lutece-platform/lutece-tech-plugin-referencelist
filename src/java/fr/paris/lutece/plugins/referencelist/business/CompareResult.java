package fr.paris.lutece.plugins.referencelist.business;

import java.util.List;
import java.util.Locale;

import fr.paris.lutece.portal.service.i18n.I18nService;

public class CompareResult {
  List<ReferenceItem> _updateListCandidateReferenceItems;
  List<ReferenceItem> _duplicateListCandidateReferenceItems;
  List<ReferenceItem> _insertListCandidateReferenceItems;
  String messageResult = "";

  private static final String INFO_REFERENCEITEM_DUPLICATE_IN_TABLE = "referencelist.info.referenceitem.import.duplicateintable";
  private static final String INFO_REFERENCEITEM_TO_UPDATE = "referencelist.info.referenceitem.import.updated";
  private static final String INFO_REFERENCEITEM_TO_INSERT = "referencelist.info.referenceitem.import.toinsert";

  public CompareResult(List<ReferenceItem> insertListCandidateReferenceItems,
      List<ReferenceItem> updateListCandidateReferenceItems, List<ReferenceItem> duplicateListCandidateReferenceItems) {
    set_updateListCandidateReferenceItems(updateListCandidateReferenceItems);
    set_duplicateListCandidateReferenceItems(duplicateListCandidateReferenceItems);
    set_insertListCandidateReferenceItems(insertListCandidateReferenceItems);
  }

  public String getMessageResult() {
    return messageResult;
  }

  public void setMessageResult(String messageResult) {
    this.messageResult = messageResult;
  }

  public List<ReferenceItem> get_updateListCandidateReferenceItems() {
    return _updateListCandidateReferenceItems;
  }

  public void set_updateListCandidateReferenceItems(List<ReferenceItem> _updateListCandidateReferenceItems) {
    this._updateListCandidateReferenceItems = _updateListCandidateReferenceItems;
  }

  public List<ReferenceItem> get_duplicateListCandidateReferenceItems() {
    return _duplicateListCandidateReferenceItems;
  }

  public void set_duplicateListCandidateReferenceItems(List<ReferenceItem> _duplicateListCandidateReferenceItems) {
    this._duplicateListCandidateReferenceItems = _duplicateListCandidateReferenceItems;
  }

  public List<ReferenceItem> get_insertListCandidateReferenceItems() {
    return _insertListCandidateReferenceItems;
  }

  public void set_insertListCandidateReferenceItems(List<ReferenceItem> _insertListCandidateReferenceItems) {
    this._insertListCandidateReferenceItems = _insertListCandidateReferenceItems;
  }

  public static String createMessage(CompareResult result) {
    String message = "";
    int update = result.get_updateListCandidateReferenceItems().size();
    int duplicate = result.get_duplicateListCandidateReferenceItems().size();
    int insert = result.get_insertListCandidateReferenceItems().size();

    if (duplicate > 0)
      message = message + "<strong>" + duplicate + "</strong> "
          + I18nService.getLocalizedString(INFO_REFERENCEITEM_DUPLICATE_IN_TABLE, Locale.getDefault()) + "<br>";
    if (update > 0)
      message = message + "<strong>" + update + "</strong> "
          + I18nService.getLocalizedString(INFO_REFERENCEITEM_TO_UPDATE, Locale.getDefault()) + " <br>";
    if (insert > 0)
      message = message + "<strong>" + insert + "</strong> "
          + I18nService.getLocalizedString(INFO_REFERENCEITEM_TO_INSERT, Locale.getDefault());
    return message;
  }
}
